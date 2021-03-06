package ua.edu.ratos.security.lti;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Slf4j
@UtilityClass
public class LTISecurityUtils {

	@SuppressWarnings("RedundantIfStatement")
	public boolean isLMSUserWithOnlyLTIRole(final Authentication auth) {
		log.debug("Challenge whether it is an LMS user with only LTI role?");
		if (auth == null)
			return false;
		if (!auth.getPrincipal().getClass().equals(LTIToolConsumerCredentials.class))
			return false;
		if (auth.getAuthorities().size() != 1)
			return false;
		if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LTI")))
			return false;
		return true;
	}

	@SuppressWarnings("RedundantIfStatement")
	public boolean isLMSUserWithFullUSERRole(Authentication auth) {
		log.debug("Challenge whether it is an LMS user with full-fledged LMS-USER role?");
		if (auth == null)
			return false;
		if (!auth.getPrincipal().getClass().equals(LTIUserConsumerCredentials.class))
			return false;
		if (auth.getAuthorities().size() != 1)
			return false;
		if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LMS-USER")))
			return false;
		return true;
	}
}
