package ua.edu.ratos.security.lti;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LTISecurityUtils {

	public boolean isLMSUserWithOnlyLTIRole(final Authentication auth) {
		log.debug("Try to decide which user is it?");
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

}
