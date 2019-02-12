package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.Student;

import java.util.Set;

@Slf4j
@Service
public class AvailabilityService {
    /**
     * Decide if this user is eligible to access this scheme;
     * To have access a student should be included to a corresponding group
     * and the group should be associated with the requested scheme
     * and the student himself has to be active.
     * @param scheme scheme
     * @param userId user attempted to take this scheme
     * @return verdict if this user can be granted access to the scheme
     */
    public boolean isSchemeAvailable(@NonNull final Scheme scheme, @NonNull final Long userId) {
        // Groups are not set for the scheme, so let it be accessible for every student
        if (scheme.getGroups().isEmpty()) return true;
        Set<Group> groups = scheme.getGroups();
        for (Group group : groups) {
            Set<Student> students = group.getStudents();
            for (Student student : students) {
                if (student.getStudId().equals(userId) && student.getUser().isActive()) {
                    log.debug("Access allowed for a user ID = {}, schemeId {}", userId, scheme.getSchemeId());
                    return true;
                }
            }
        }
        log.warn("Access rejected for a user ID = {}, schemeId = {}", userId, scheme.getSchemeId());
        return false;
    }
}
