package ua.edu.ratos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.edu.ratos.security.lti.LTIToolConsumerCredentials;

import java.util.Set;
import java.util.stream.Collectors;

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
     * Obtain ID of the faculty to which the current authenticated staff belongs
     * For Unit-testing: fallback to default values
     * @return facId
     */
    public Long getAuthFacId() {
        if ("h2".equals(profile) || "mysql".equals(profile)) return 1L;
        Authentication auth = getStaffAuthentication();
        return ((AuthenticatedStaff) auth.getPrincipal()).getFacId();
    }

    /**
     * Obtain ID of the organization to which the current authenticated staff belongs
     * For Unit-testing: fallback to default values
     * @return orgId
     */
    public Long getAuthOrgId() {
        if ("h2".equals(profile) || "mysql".equals(profile)) return 1L;
        Authentication auth = getStaffAuthentication();
        return ((AuthenticatedStaff) auth.getPrincipal()).getOrgId();
    }

    /**
     * Obtain ID of LMS a user is authenticated from
     * @return lmsId
     */
    public Long getAuthLmsId() {
        if ("h2".equals(profile) || "mysql".equals(profile)) return 1L;
        Authentication auth = getLmsAuthentication();
        return ((LTIToolConsumerCredentials) auth.getPrincipal()).getLmsId();
    }

    /**
     * Obtain ID of currently authenticated student
     * For Unit-testing: fallback to default values, 2L
     * @return studId
     */
    public Long getAuthStudId() {
        if ("h2".equals(profile) || "mysql".equals(profile)) return 2L;
        Authentication auth = getAuthentication();
        if (!(auth.getPrincipal() instanceof AuthenticatedUser))
            throw new SecurityException("Lack of authority");
        return ((AuthenticatedUser) auth.getPrincipal()).getUserId();
    }


    /**
     * Obtain all granted roles for the current authenticated staff
     * @return set of roles
     */
    public Set<String> getAuthStaffRoles() {
        Authentication auth = getStaffAuthentication();
        return ((AuthenticatedStaff) auth.getPrincipal()).getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
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

    private Authentication getLmsAuthentication() {
        Authentication auth = getAuthentication();
        if (!(auth.getPrincipal() instanceof LTIToolConsumerCredentials))
            throw new SecurityException("Lack of authority");
        return auth;
    }

}
