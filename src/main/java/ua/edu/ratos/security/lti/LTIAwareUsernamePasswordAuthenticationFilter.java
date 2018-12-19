package ua.edu.ratos.security.lti;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.ratos.security.AuthenticatedUser;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Overrides the default behaviour of UsernamePasswordAuthenticationFilter in order to pick up specific LTI information
 * wired into a corresponding Authentication object. In order to do so, we check for the presence of LMS authentication
 * and if exists, remember this authentication, clear security context, try to authenticate with UsernamePasswordAuthenticationFilter
 * and(if successful) add the previous LTI-specific authentication to the new authentication. That's it.
 */
@Slf4j
public class LTIAwareUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Setter
    private LTISecurityUtils ltiSecurityUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication previousAuth = SecurityContextHolder.getContext().getAuthentication();
        // Check for LMS LTI v1p0 authentication in place
        if (ltiSecurityUtils.isLMSUserWithOnlyLTIRole(previousAuth)) {
            log.debug("LTI authentication exists, try to re-authenticate with UsernamePasswordAuthenticationFilter as normal");
            SecurityContextHolder.clearContext();
            Authentication authentication = null;
            try {// Attempt to authenticate with standard UsernamePasswordAuthenticationFilter
                authentication = super.attemptAuthentication(request, response);
            } catch (AuthenticationException e) {
                // If fails by throwing an exception, catch it in unsuccessfulAuthentication() method
                log.debug("Failed to upgrade authentication for an LTI user with UsernamePasswordAuthenticationFilter");
                SecurityContextHolder.getContext().setAuthentication(previousAuth);
                throw e;
            }
            log.debug("Obtained a valid re-authentication for LTI user with UsernamePasswordAuthenticationFilter");
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
            Long userId = authenticatedUser.getUserId();
            String email = authenticatedUser.getUsername();
            Collection<GrantedAuthority> newAuthorities = authenticatedUser.getAuthorities();

            // This is castable for sure, see above check
            LTIToolConsumerCredentials ltiToolConsumerCredentials = (LTIToolConsumerCredentials) previousAuth.getPrincipal();
            LTIUserConsumerCredentials ltiUserConsumerCredentials = LTIUserConsumerCredentials.create(userId, email, ltiToolConsumerCredentials);

            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(newAuthorities);
            updatedAuthorities.addAll(previousAuth.getAuthorities());

            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    ltiUserConsumerCredentials, previousAuth.getCredentials(), Collections.unmodifiableList(updatedAuthorities));
            log.debug("Created an updated authentication for an LTI user with ID :: {}", userId);
            return newAuth;
        }
        log.debug("No LTI authentication exists, try to authenticate with UsernamePasswordAuthenticationFilter in the usual way");
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        Authentication previousAuth = SecurityContextHolder.getContext().getAuthentication();
        if (ltiSecurityUtils.isLMSUserWithOnlyLTIRole(previousAuth)) {
            log.debug("Unsuccessful authentication upgrade for an LTI user");
            super.unsuccessfulAuthentication(request, response, failed);
            log.debug("Fallback to previous authentication :: {}", previousAuth);
            SecurityContextHolder.getContext().setAuthentication(previousAuth);
        } else {
            log.debug("Unsuccessful authentication for non-LTI user with UsernamePasswordAuthenticationFilter");
            super.unsuccessfulAuthentication(request, response, failed);
        }
    }

}
