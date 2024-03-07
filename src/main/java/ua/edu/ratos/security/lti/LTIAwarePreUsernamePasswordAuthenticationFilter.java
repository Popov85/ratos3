package ua.edu.ratos.security.lti;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class LTIAwarePreUsernamePasswordAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Doing custom filter pre processing, auth = {}", auth);
        if (LTISecurityUtils.isLMSUserWithOnlyLTIRole(auth)) {
            // Remember LTI auth
            servletRequest.setAttribute("auth", auth);
            // Reset it and let next filter to authenticate
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("LTIPreUsernamePasswordAuthenticationFilter destroy");
    }
}
