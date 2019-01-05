package ua.edu.ratos.security;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.repository.StaffRepository;
import ua.edu.ratos.dao.repository.StudentRepository;

@Slf4j
@Component
@Transactional(readOnly = true)
public class AuthenticatedUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    private final StaffRepository staffRepository;

    @Autowired
    public AuthenticatedUserDetailsService(final StudentRepository studentRepository, final StaffRepository staffRepository) {
        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull final String email) throws UsernameNotFoundException {
        // try to authenticate student
        final Student student = studentRepository.findByIdForAuthentication(email);
        if (student!=null) {
            final AuthenticatedUser authenticatedStudent = AuthenticatedUser.create(student);
            log.debug("Found student :: {}", authenticatedStudent);
            return authenticatedStudent;
        } else {
            // try to authenticate staff
            final Staff staff = staffRepository.findByIdForAuthentication(email);
            if (staff!=null) {
                AuthenticatedStaff authenticatedStaff = AuthenticatedStaff.create(staff);
                log.debug("Found staff :: {}", authenticatedStaff);
                return authenticatedStaff;
            } else {
                log.warn("Failed to find any user by email :: {}", email);
                throw new UsernameNotFoundException("Failed to authorize by email");
            }
        }
    }
}
