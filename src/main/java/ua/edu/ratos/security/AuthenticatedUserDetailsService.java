package ua.edu.ratos.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.domain.entity.Student;
import ua.edu.ratos.domain.repository.StaffRepository;
import ua.edu.ratos.domain.repository.StudentRepository;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Transactional(readOnly = true)
public class AuthenticatedUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // try to authenticate student
        final Student student = studentRepository.findByIdForAuthentication(email);
        if (student!=null) {
            final AuthenticatedUser authenticatedStudent = new AuthenticatedUser(student.getUser().getEmail(), new String(student.getUser().getPassword()), getAuthorities(student.getUser()))
                    .setUserId(student.getStudId());
            log.debug("Found student :: {}", authenticatedStudent);
            return authenticatedStudent;
        } else {
            // try to authenticate staff
            final Staff staff = staffRepository.findByIdForAuthentication(email);
            if (staff!=null) {
                AuthenticatedStaff authenticatedStaff = new AuthenticatedStaff(staff.getUser().getEmail(), new String(staff.getUser().getPassword()), getAuthorities(staff.getUser()));
                authenticatedStaff.setUserId(staff.getStaffId());
                authenticatedStaff
                        .setDepId(staff.getDepartment().getDepId())
                        .setFacId(staff.getDepartment().getFaculty().getFacId())
                        .setOrgId(staff.getDepartment().getFaculty().getOrganisation().getOrgId());
                log.debug("Found staff :: {}", authenticatedStaff);
                return authenticatedStaff;
            } else {
                log.debug("Failed to find any user by email, {}", email);
                throw new UsernameNotFoundException("Failed to authorize by email");
            }
        }
    }

    private Set<GrantedAuthority> getAuthorities(ua.edu.ratos.domain.entity.User user){
        Set<GrantedAuthority> authorities =  new HashSet<>();
        if (user.getRoles()==null || user.getRoles().isEmpty()) return authorities;
        user.getRoles().forEach(a -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(a.getName());
            authorities.add(grantedAuthority);
        });
        return authorities;
    }
}
