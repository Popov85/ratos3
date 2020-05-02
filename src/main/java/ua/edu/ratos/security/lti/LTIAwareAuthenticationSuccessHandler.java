package ua.edu.ratos.security.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.edu.ratos.security.AuthenticatedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Slf4j
@Component
public class LTIAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Object previousAuth = request.getAttribute("auth");
        log.debug("Ratos attribute = {}", previousAuth!=null ? (Authentication)previousAuth: null);
        // Merge authentication if previous LTI (OAuth 1.0) exists;
        if (previousAuth !=null) {
            authentication = mergeAuthentication((Authentication) previousAuth, authentication);
            log.debug("Put merged auth into session = {}", authentication);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        }
        super.onAuthenticationSuccess(request, response, authentication);
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
        log.debug("Merged authentication (LTI+new USER) for the user ID = {}", userId);
        return resultingAuthentication;
    }
}
