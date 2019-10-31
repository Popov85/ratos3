package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;

/**
 * There are such business rules for the RATOS project:
 *
 * There are two access levels: dep-private and private;
 *    RATOS does not allow instructors of different departments to modify learning elements of other departments with
 *    <b>dep-private</b> access (default), despite UI does not provide this opportunity, hacks are quite possible;
 *    RATOS does not allow instructors of the same department to modify learning elements with <b>private</b> access;
 */
@Slf4j
@Component
@AllArgsConstructor
public class AccessChecker {

    private static final String DEP_PRIVATE_ACCESS_MESSAGE = "Failed to modify: element has a dep-private access";
    private static final String PRIVATE_ACCESS_MESSAGE = "Failed to modify: element has a private access";

    private final SecurityUtils securityUtils;

    public void checkModifyAccess(@NonNull final Access accessLevel, @NonNull final Staff staff) {
        String access = accessLevel.getName();
        if ("dep-private".equals(access)) {
            Long depId = staff.getDepartment().getDepId();
            if (!depId.equals(securityUtils.getAuthDepId())) {
                log.warn("Attempt to modify element with dep-private access, staff ID = {}", staff.getStaffId());
                throw new RuntimeException(DEP_PRIVATE_ACCESS_MESSAGE);
            }
        }
        if ("private".equals(access)) {
            Long staffId = staff.getStaffId();
            if (!staffId.equals(securityUtils.getAuthStaffId())) {
                log.warn("Attempt to modify element with private access, staff ID = {}", staff.getStaffId());
                throw new RuntimeException(PRIVATE_ACCESS_MESSAGE);
            }
        }
    }
}
