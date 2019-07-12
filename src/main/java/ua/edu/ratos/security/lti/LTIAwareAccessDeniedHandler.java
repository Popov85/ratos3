package ua.edu.ratos.security.lti;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class LTIAwareAccessDeniedHandler implements AccessDeniedHandler {

	private final LTISecurityUtils ltiSecurityUtils;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (ltiSecurityUtils.isLMSUserWithOnlyLTIRole(auth)) {
			log.debug("Detected an LTI user lacking authority trying to access protected resource, redirection to /login endpoint");
			// Remember the request pathway
			RequestCache requestCache = new HttpSessionRequestCache();
			requestCache.saveRequest(request, response);
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		log.debug("Detected a non-LTI user lacking authority trying to access protected resource, redirection to /access-denied endpoint");
		response.sendRedirect(request.getContextPath() + "/access-denied");
	}

}
