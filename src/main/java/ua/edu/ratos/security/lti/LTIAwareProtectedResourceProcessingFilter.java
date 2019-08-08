package ua.edu.ratos.security.lti;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;

@Slf4j
@AllArgsConstructor
public class LTIAwareProtectedResourceProcessingFilter extends ProtectedResourceProcessingFilter {

    /**
     * Default behavior is to reset the Authentication to the previous state that was before doing this filter.
     * So if previous authentication was null, it will be again null after this filter has done its job.
     * Now we need to override this, by resetting back to previous only in case of failed attempt to authenticate with OAuth
     * Otherwise let this new authentication to remain and create authentication session
     * with ROLE_LTI (or ROLE_LMS-USER if e-mail parameter has proven the user's identity)
     * Also, in case we already have an authenticated full-fledged user (LMS-USER) before the launch,
     * do not lose his authentication,
     * but rather merge the new OAuth launch credentials with the existing LMS-USER role.
     * @param previousAuthentication previous authentication that was in the security context before running the launch request
     */
    @Override
    protected void resetPreviousAuthentication(Authentication previousAuthentication) {
        // Get newly created OAuth authentication during launch request
        Authentication oauthAuthentication = SecurityContextHolder.getContext().getAuthentication();
        // If OAuth authentication failed and no authentication now is present in the context, reset to previous one
        if (oauthAuthentication == null || oauthAuthentication.getPrincipal()==null ) {
            super.resetPreviousAuthentication(previousAuthentication);
            return;
        }
        // If OAuth authentication just provided only LTI role (but failed to provide LMS-USER role)
        // and previous authentication was LMS-USER, merge them and set to the context
        if (LTISecurityUtils.isLMSUserWithOnlyLTIRole(oauthAuthentication)
                && LTISecurityUtils.isLMSUserWithFullUSERRole(previousAuthentication)) {
            log.debug("Try to merge authentication (existing LMS-USER and new LTI)");
            Authentication resultingAuthentication = mergeAuthentication(previousAuthentication, oauthAuthentication);
            super.resetPreviousAuthentication(resultingAuthentication);
        }
    }


    /**
     * What if already fully authenticated user jumps to another LMS course/module/theme and tries to take tests.
     * Here we have a new OAuth credentials with a new remote endpoint to sent score.
     * We cannot lose this information cause we will be unable to send the score for this test.
     * The solution is to merge the new OAuth credentials with the existing fully authenticated user credentials
     * @param previousAuthentication
     * @param oauthAuthentication
     * @return merged authentication object
     */
    private Authentication mergeAuthentication(Authentication previousAuthentication, Authentication oauthAuthentication) {
        LTIToolConsumerCredentials updatedPrincipal = (LTIToolConsumerCredentials)oauthAuthentication.getPrincipal();
        LTIUserConsumerCredentials previousPrincipal = (LTIUserConsumerCredentials) previousAuthentication.getPrincipal();

        LTIUserConsumerCredentials resultingPrincipal = LTIUserConsumerCredentials
                .create(previousPrincipal.getUserId(), updatedPrincipal.getLmsId(), updatedPrincipal.getConsumerCredentials());
        resultingPrincipal.setEmail(previousPrincipal.getEmail().orElse(null));
        resultingPrincipal.setUser(updatedPrincipal.getUser().orElse(null));
        resultingPrincipal.setOutcome(updatedPrincipal.getOutcome().orElse(null));

        SignatureSecret updatedSignatureSecret = (SignatureSecret) oauthAuthentication.getCredentials();

        // We have an updated principal and updated signature

        Authentication resultingAuthentication =
                new UsernamePasswordAuthenticationToken(resultingPrincipal, updatedSignatureSecret, previousAuthentication.getAuthorities());
        log.debug("Merged authentication (Password+new LTI) for user ID = {} ", previousPrincipal.getUserId());
        return resultingAuthentication;
    }
}
