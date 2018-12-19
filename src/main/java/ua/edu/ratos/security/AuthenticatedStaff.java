package ua.edu.ratos.security;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import ua.edu.ratos.dao.entity.Staff;
import java.util.Collection;

/**
 * Inject Principal to controller method, cast it to this class and get all the perks such as staffId, depId, etc..
 */
@Getter
@ToString(callSuper = true)
public class AuthenticatedStaff extends AuthenticatedUser {

    private Long depId;
    private Long facId;
    private Long orgId;

    protected AuthenticatedStaff(Long staffId, Long depId, Long facId, Long orgId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(staffId, username, password, authorities);
        this.depId = depId;
        this.facId = facId;
        this.orgId = orgId;
    }

    public static AuthenticatedStaff create(@NonNull final Staff staff) {
        return new AuthenticatedStaff(staff.getStaffId(),
                staff.getDepartment().getDepId(),
                staff.getDepartment().getFaculty().getFacId(),
                staff.getDepartment().getFaculty().getOrganisation().getOrgId(),
                staff.getUser().getEmail(),
                new String(staff.getUser().getPassword()),
                getAuthorities(staff.getUser()));
    }
}
