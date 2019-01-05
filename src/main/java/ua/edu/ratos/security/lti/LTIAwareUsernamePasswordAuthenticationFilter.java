package ua.edu.ratos.security.lti;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.ratos.security.AuthenticatedUser;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Overrides the default behaviour of UsernamePasswordAuthenticationFilter in order to pick up specific LTI information
 * wired into a corresponding Authentication object. In order to do so, we check for the presence of LTI authentication
 * and if exists, remember this authentication, clear security context, try to authenticate with UsernamePasswordAuthenticationFilter
 * and(if successful) add the previous LTI-specific authentication to the new authentication. That's it.
 */
@Slf4j
@AllArgsConstructor
public class LTIAwareUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final LTISecurityUtils ltiSecurityUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication previousAuth = SecurityContextHolder.getContext().getAuthentication();
        // Check for LMS LTI v1p0 authentication in place
        if (ltiSecurityUtils.isLMSUserWithOnlyLTIRole(previousAuth)) {
            log.debug("LTI authentication exists, try to authenticate with UsernamePasswordAuthenticationFilter in the usual way");
            SecurityContextHolder.clearContext();
            Authentication passwordAuthentication;
            try {// Attempt to authenticate with standard UsernamePasswordAuthenticationFilter
                passwordAuthentication = super.attemptAuthentication(request, response);
            } catch (AuthenticationException e) {
                // If fails by throwing an exception, catch it in unsuccessfulAuthentication() method
                log.debug("Failed to upgrade authentication with UsernamePasswordAuthenticationFilter");
                SecurityContextHolder.getContext().setAuthentication(previousAuth);
                throw e;
            }
            log.debug("Try to merge authentication (existing LTI and new USER)");
            return mergeAuthentication(previousAuth, passwordAuthentication);
        }
        log.debug("No LTI authentication exists, try to authenticate with UsernamePasswordAuthenticationFilter in the usual way");
        return super.attemptAuthentication(request, response);
    }


    private Authentication mergeAuthentication(Authentication previousAuth, Authentication passwordAuth) {
        AuthenticatedUser passwordPrincipal = (AuthenticatedUser) passwordAuth.getPrincipal();
        Long userId = passwordPrincipal.getUserId();
        String email = passwordPrincipal.getUsername();

        LTIToolConsumerCredentials previousPrincipal = (LTIToolConsumerCredentials) previousAuth.getPrincipal();
        LTIUserConsumerCredentials resultingPrincipal =
                LTIUserConsumerCredentials.create(userId, previousPrincipal.getLmsId(), previousPrincipal);
        resultingPrincipal.setOutcome(previousPrincipal.getOutcome().orElse(null));
        resultingPrincipal.setUser(previousPrincipal.getUser().orElse(null));
        resultingPrincipal.setEmail(email);

        List<GrantedAuthority> updatedAuthorities = AuthorityUtils.createAuthorityList("ROLE_LMS-USER");

        Authentication resultingAuthentication = new UsernamePasswordAuthenticationToken(
                resultingPrincipal, previousAuth.getCredentials(), Collections.unmodifiableList(updatedAuthorities));
        log.debug("Merged authentication (LTI+new USER) for the user ID :: {} ", userId);
        return resultingAuthentication;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        Authentication previousAuth = SecurityContextHolder.getContext().getAuthentication();
        if (ltiSecurityUtils.isLMSUserWithOnlyLTIRole(previousAuth)) {
            log.debug("Unsuccessful authentication upgrade for an LTI user, fallback to previous authentication");
            super.unsuccessfulAuthentication(request, response, failed);
            SecurityContextHolder.getContext().setAuthentication(previousAuth);
        } else {
            log.debug("Unsuccessful authentication for a non-LTI user with UsernamePasswordAuthenticationFilter");
            super.unsuccessfulAuthentication(request, response, failed);
        }
    }

}
