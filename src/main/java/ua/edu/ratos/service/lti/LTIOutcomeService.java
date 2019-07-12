package ua.edu.ratos.service.lti;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthClientHttpRequestFactory;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.security.lti.LTIOutcomeParams;
import ua.edu.ratos.security.lti.LTIUserConsumerCredentials;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class LTIOutcomeService {

    private final AppProperties appProperties;

    private final XMLRequestBodyBuilder xmlRequestBodyBuilder;

    private final LTIRetryOutcomeService ltiRetryOutcomeService;

    private final SecurityUtils securityUtils;

    @Autowired
    public LTIOutcomeService(@NonNull final AppProperties appProperties,
                             @NonNull final XMLRequestBodyBuilder xmlRequestBodyBuilder,
                             @NonNull final LTIRetryOutcomeService ltiRetryOutcomeService, @NonNull final SecurityUtils securityUtils) {
        this.appProperties = appProperties;
        this.xmlRequestBodyBuilder = xmlRequestBodyBuilder;
        this.ltiRetryOutcomeService = ltiRetryOutcomeService;
        this.securityUtils = securityUtils;
    }

    /**
     * Sends score to Learning Management System (LMS) if it was configured to allow sending outcomes,
     * (outcome parameters were present in the initial launch request)
     * @param schemeId   id of the scheme that the user has just taken
     * @param percent    gained during learning session percent
     * @param protocol   actual network protocol to perform post request
     * @see <a href="https://www.imsglobal.org/specs/ltiv1p1p1/implementation-guide#toc-3">LTI v 1.1.1</a>
     */
    @Async
    public void sendOutcome(final Long schemeId, final Double percent, final String protocol) {
        LTIUserConsumerCredentials principal = securityUtils.getLmsUserAuthentication();
        String email = principal.getEmail().orElse("unknown e-mail");
        Optional<LTIOutcomeParams> outcome = principal.getOutcome();
        if (!outcome.isPresent())
            throw new RuntimeException("Outcome parameters are not included by LMS and result was not sent back to LMS!" +
                    " result on schemeId = "+schemeId+ " email = "+email+" with outcome = "+percent);
        OAuthRestTemplate authRestTemplate = getOAuthRestTemplate(principal);
        String sourcedId = outcome.get().getSourcedId();
        String outcomeURL = outcome.get().getOutcomeURL();
        String actualOutcomeURL = outcomeURL;
        if (appProperties.getLti().isLaunchUrlFix()) actualOutcomeURL = fixProtocol(outcomeURL, protocol);
        URI uri = UriComponentsBuilder.fromUriString(actualOutcomeURL).build().toUri();
        // Just to keep things simple, create a value of milliseconds since 1970
        String messageIdentifier = Long.toString(new Date().getTime());
        // Convert to score between 0-1 as per LTI v1.1.1 specification
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String textScore = decimalFormat.format(percent / 100);
        log.debug("Calculated textScore to be sent to LMS = {}", textScore);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        String body = xmlRequestBodyBuilder.build(sourcedId, messageIdentifier, textScore);
        // Add additional oauth_body_hash parameter as per OAuth extension
        addAdditionalParam(authRestTemplate, body);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        // Try to send multiple times for sure
        ltiRetryOutcomeService.doSend(authRestTemplate, uri, request, email, schemeId);
        log.debug("Outcome is sent to LMS server");
    }

    /**
     * Create a client-secret-aware rest template instance for posting score to LMS
     * @param principal      extracted from authentication principal object holding security information (consumer key)
     * @return rest template with wired client secret
     */
    private OAuthRestTemplate getOAuthRestTemplate(LTIUserConsumerCredentials principal) {
        BaseProtectedResourceDetails resourceDetails = new BaseProtectedResourceDetails();
        resourceDetails.setConsumerKey(principal.getConsumerKey());
        resourceDetails.setSharedSecret(securityUtils.getOauthSignatureSecret());
        OAuthRestTemplate authRestTemplate = new OAuthRestTemplate(resourceDetails);
        return authRestTemplate;
    }


    /**
     * In some cases, LMS expects TP to send the outcome to a different protocol than is actually used;
     * This service method is optional, decide if you need to use it, if not just omit it
     * e.g. (actual HTTP & expected HTTP)
     *
     * @param initialURL,    like  https://open-edx.org/outcome-service
     * @param actualProtocol {http, https, ftp}
     * @return fixed URL
     */
    private String fixProtocol(String initialURL, String actualProtocol) {
        int end = initialURL.indexOf(':');
        String initialProtocol = initialURL.substring(0, end);
        log.debug("Initial protocol = {}, actual protocol = {} ", initialProtocol, actualProtocol);
        if (!initialProtocol.equals(actualProtocol)) {
            // replace the initial protocol with the actual one
            String fixedURL = initialURL.replace(initialProtocol, actualProtocol);
            log.debug("Fixed url = {}", fixedURL);
            return fixedURL;
        }
        return initialURL;
    }

    /**
     * Add an additional parameter, specifically: oauth_body_hash
     *
     * @param authRestTemplate rest template with wired client secret
     * @param body             the body or http request for posting a score
     */
    private void addAdditionalParam(OAuthRestTemplate authRestTemplate, String body) {
        OAuthClientHttpRequestFactory requestFactory = (OAuthClientHttpRequestFactory) authRestTemplate.getRequestFactory();
        Map<String, String> additionalOAuthParameters = new HashMap<>();
        String hash = doXMLBodyHashSHA1(body);
        additionalOAuthParameters.put("oauth_body_hash", hash);
        requestFactory.setAdditionalOAuthParameters(additionalOAuthParameters);
        log.debug("oauth_body_hash = " + hash);
    }

    /**
     * As per LTI v 1.1.1 specification the request's XML body has to be hashed with SHA-1;
     * The oauth_body_hash [OBH, 11] is computed using a SHA-1 hash of the body contents and added to the Authorization header.
     *
     * @param body plain text XML body
     * @return hashed XML request body
     */
    private String doXMLBodyHashSHA1(String body) {
        String algorithmCode = "SHA-1";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithmCode);
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to hash XML body = {}", body);
            throw new RuntimeException("Failed to hash XML body", e);
        }
        md.update(body.getBytes());
        byte[] output = Base64.encodeBase64(md.digest());
        String hash = new String(output);
        return hash;
    }
}
