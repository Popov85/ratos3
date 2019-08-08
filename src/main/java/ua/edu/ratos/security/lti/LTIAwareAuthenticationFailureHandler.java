package ua.edu.ratos.security.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LTIAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        Object previousObj = request.getAttribute("auth");
        if (previousObj!=null && LTISecurityUtils.isLMSUserWithOnlyLTIRole((Authentication) previousObj)) {
            log.debug("Unsuccessful authentication upgrade for an LTI user, fallback to previous authentication");
            SecurityContextHolder.getContext().setAuthentication((Authentication) previousObj);
        } else {
            log.debug("Unsuccessful authentication for a non-LTI user with UsernamePasswordAuthenticationFilter");
            SecurityContextHolder.clearContext();
        }
        // Replace default behaviour with return 401 Unauthorized
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
