package ua.edu.ratos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Convenience security methods for usage throughout the app
 * @see "https://stackoverflow.com/questions/25713315/spring-security-get-login-user-within-controllers-good-manners"
 */
@Component
public class SecurityUtils {

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Obtain ID of the current authenticated staff
     * For Unit-testing: fallback to default values
     * @return staffId
     */
    public Long getAuthStaffId() {
        if ("h2".equals(profile) || "mysql".equals(profile)) return 1L;
        Authentication auth = getStaffAuthentication();
        return ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
    }

    /**
     * Obtain ID of the department to which the current authenticated staff belongs
     * For Unit-testing: fallback to default values
     * @return depId
     */
    public Long getAuthDepId() {
        if ("h2".equals(profile) || "mysql".equals(profile)) return 1L;
        Authentication auth = getStaffAuthentication();
        return ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
    }

    /**
     * Obtain ID of currently authenticated student
     * For Unit-testing: fallback to default values
     * @return studId
     */
    public Long getAuthStudId() {
        if ("h2".equals(profile) || "mysql".equals(profile)) return 1L;
        Authentication auth = getAuthentication();
        if (!(auth.getPrincipal() instanceof AuthenticatedUser))
            throw new SecurityException("Lack of authority");
        return ((AuthenticatedUser) auth.getPrincipal()).getUserId();
    }


    private Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth==null) throw new SecurityException("Unauthorized");
        return auth;
    }

    private Authentication getStaffAuthentication() {
        Authentication auth = getAuthentication();
        if (!(auth.getPrincipal() instanceof AuthenticatedStaff))
            throw new SecurityException("Lack of authority");
        return auth;
    }

}
