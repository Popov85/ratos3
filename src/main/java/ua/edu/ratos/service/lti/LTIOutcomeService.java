package ua.edu.ratos.service.lti;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.stereotype.Service;
import ua.edu.ratos.security.lti.LTIOutcomeParams;
import ua.edu.ratos.security.lti.LTIUserConsumerCredentials;
import ua.edu.ratos.service.lti.domain.*;
import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class LTIOutcomeService {
    // Not a bean as it affects the global configuration
    // For controllers, in http request, use "Accept" header to specify which output format is wanted
    // As per LTI 1.1.1 specification only XML format type is accepted
    private XmlMapper xmlMapper;

    @PostConstruct
    public void init() {
        xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
    }

    /**
     * Sends score to Learning Management System (LMS) if it was configured to allow sending outcomes,
     * (outcome parameters were present in the initial launch request)
     * @param authentication security object containing launch request information
     * @param score score between 0-1 as per LTI v1.1.1 specification
     * @see <a href="https://www.imsglobal.org/specs/ltiv1p1p1/implementation-guide#toc-3">LTI v 1.1.1</a>
     * @throws Exception
     */
    public void sendOutcome(@NonNull final Authentication authentication, @NonNull final Double score) throws Exception {
        LTIUserConsumerCredentials principal = (LTIUserConsumerCredentials)authentication.getPrincipal();
        Optional<LTIOutcomeParams> outcome = principal.getOutcome();

        if (!outcome.isPresent()) {
            log.debug("Outcome parameters are not included, result is not sent to LMS :: {}", score);
            return;
        }

        // Create a client secret-aware rest template for posting score to LMS
        BaseProtectedResourceDetails resourceDetails = new BaseProtectedResourceDetails();
        resourceDetails.setConsumerKey(principal.getConsumerKey());
        resourceDetails.setSharedSecret((SignatureSecret) authentication.getCredentials());
        OAuthRestTemplate authRestTemplate = new OAuthRestTemplate(resourceDetails);

        String sourcedId = outcome.get().getSourcedId();
        String outcomeURL = outcome.get().getOutcomeURL();

        // Just to keep things simple, create a value of milliseconds since 1970
        String messageIdentifier = Long.toString(new Date().getTime());
        String textScore = Double.toString(score);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        IMSXPOXEnvelopeRequest envelopeRequest = getEnvelopeRequest(sourcedId, messageIdentifier, textScore);
        String body = xmlMapper.writeValueAsString(envelopeRequest);

        HttpEntity<String> request = new HttpEntity<String>(body, headers);

        ResponseEntity<String> result = authRestTemplate.postForEntity(new URI(outcomeURL),request, String.class);

        log.debug("Result :: {} is sent with status code :: {}", score, result.getStatusCode());
    }

    /**
     * Create a Java-object ready for XML serialization according to LTI v1.1.1 specification
     * @param sourcedId a value needed to posting score to LMS as per LTI specification
     * @param messageIdentifier Some value for identifying messages (in our case the number of milliseconds since 1970)
     * @param textScore score between 0-1 gained by a user after learning session completion
     * @return fully populated object ready to be XML-serialised according to LTI specification
     * @see <a href="https://www.imsglobal.org/specs/ltiv1p1p1/implementation-guide#toc-3">LTI v 1.1.1</a>
     */
    private IMSXPOXEnvelopeRequest getEnvelopeRequest(String sourcedId, String messageIdentifier, String textScore) {
        return new IMSXPOXEnvelopeRequest()
                .setIMSXPOXHeader(new IMSXPOXHeader()
                        .setImsxPOXRequestHeaderInfo(new IMSXPOXRequestHeaderInfo()
                                .setImsxMessageIdentifier(messageIdentifier)))
                .setIMSXPOXBody(new IMSXPOXBody()
                        .setReplaceResultRequest(new ReplaceResultRequest()
                                .setResultRecord(new ResultRecord()
                                        .setSourcedGUID(new SourcedGUID()
                                                .setSourcedId(sourcedId))
                                        .setResult(new Result()
                                                .setResultScore(new ResultScore()
                                                        .setTextString(textScore))))));
    }
}
