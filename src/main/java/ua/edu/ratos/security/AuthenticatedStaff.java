package ua.edu.ratos.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

/**
 * Inject Principal to controller method, cast it to this class and get all the perks such as staffId, depId, etc..
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class AuthenticatedStaff extends User {

    private Long staffId;
    private Long depId;
    private Long facId;
    private Long orgId;

    public AuthenticatedStaff(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
