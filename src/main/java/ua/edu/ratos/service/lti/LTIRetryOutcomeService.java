package ua.edu.ratos.service.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ua.edu.ratos.service.lti.response.IMSXPOXEnvelopeResponse;
import ua.edu.ratos.service.lti.response.IMSXStatusInfo;
import java.net.URI;

@Slf4j
@Service
public class LTIRetryOutcomeService {
	/**
	 * Tries to post the outcome multiple times just to be on the safe side;
	 * Response arrives in XML format, see interpretation below
	 * @param oAuthRestTemplate rest template with wired security
	 * @param uri uri to where to send the score
	 * @param entity ready in object to be sent to LMS to post a score
	 * @see <a href="https://www.imsglobal.org/gws/gwsv1p0/imsgws_wsdlBindv1p0.html"> IMS General Web Services WSDL Binding Guidelines</a>
	 */
	@Async
	@SuppressWarnings("SpellCheckingInspection")
	@Retryable(maxAttempts = 6, value = RestClientException.class, backoff = @Backoff(delay = 500, multiplier = 2))
	public void doSend(OAuthRestTemplate oAuthRestTemplate, URI uri, HttpEntity<String> entity, String email, Long schemeId) {
		IMSXPOXEnvelopeResponse response = oAuthRestTemplate.postForObject(uri, entity, IMSXPOXEnvelopeResponse.class);
		IMSXStatusInfo imsxStatusInfo = response.getIMSXPOXHeader().getImsxPOXResponseHeaderInfo().getImsxStatusInfo();
		String imsxCodeMajor = imsxStatusInfo.getImsxCodeMajor();
		String imsxDescription = imsxStatusInfo.getImsxDescription();
		if (!"success".equals(imsxCodeMajor)) {
			log.warn("Outcome was rejected by LMS server by user :: {}, having taken the scheme :: {}, with code :: {}, and description :: {} ",
					email,  schemeId, imsxCodeMajor, imsxDescription);
			return;
		}
		log.debug("Outcome was successfully accepted by LMS server, by user :: {}, having taken the scheme :: {}, with code :: {}, and description :: {}",
				email, schemeId, imsxCodeMajor, imsxDescription);
	}

	@Recover
	@SuppressWarnings("unused")
	public void recover(OAuthRestTemplate oAuthRestTemplate, URI uri, HttpEntity<String> entity, String email, Long schemeId) {
		log.error("Failed to send the outcome to LMS by user :: {}, having taken the scheme :: {}, with reason :: probably the LTI service is off",
				email, schemeId);
	}

}
