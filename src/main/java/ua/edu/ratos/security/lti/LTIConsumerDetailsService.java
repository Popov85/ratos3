package ua.edu.ratos.security.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LTICredentials;
import ua.edu.ratos.dao.repository.lms.LTICredentialsRepository;

@Slf4j
@Component
public class LTIConsumerDetailsService implements ConsumerDetailsService {

    @Autowired
    private LTICredentialsRepository ltiCredentialsRepository;

	@Override
	public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) throws OAuthException {
        LTICredentials ltiCredentials = ltiCredentialsRepository.findByConsumerKey(consumerKey);
        if (ltiCredentials!=null) {
            BaseConsumerDetails cd = new BaseConsumerDetails();
            cd.setConsumerName("LMS");
            cd.setConsumerKey(consumerKey);
            cd.setSignatureSecret(new SharedConsumerSecretImpl(ltiCredentials.getSecret()));
            cd.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_LTI"));
            // no token required (0-legged)
            cd.setRequiredToObtainAuthenticatedToken(false);
            log.debug("LTI success: found the client secret and created basic ConsumerDetails object, {}", cd);
            return cd;
        }
        throw new OAuthException("LTI failure: no client secret matching consumer key was found in DB");
	}

}
