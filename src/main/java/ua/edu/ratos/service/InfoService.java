package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.security.AuthenticatedUser;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.out.DepartmentMinOutDto;
import ua.edu.ratos.service.dto.out.PositionOutDto;
import ua.edu.ratos.service.dto.out.StaffInfoOutDto;
import ua.edu.ratos.service.dto.out.UserInfoOutDto;

import java.util.Set;

@Service
@AllArgsConstructor
public class InfoService {

    private final SecurityUtils securityUtils;

    public UserInfoOutDto getUserInfo() {
        AuthenticatedUser user = securityUtils.getAuthUser();
        // ID
        Long userId = user.getUserId();
        // email
        String email = user.getUsername();
        // name
        String name = user.getName();
        // surname
        String surname = user.getSurname();
        // role
        String role = securityUtils.getAuthRole();
        // LTI
        boolean lti = securityUtils.isLtiUser();
        UserInfoOutDto dto = new UserInfoOutDto()
                .setUserId(userId)
                .setName(name)
                .setSurname(surname)
                .setEmail(email)
                .setRole(role)
                .setLms(lti);
        if (!securityUtils.isCurrentUserStudent()) {
            // Staff goes here, retrieve position and department
            AuthenticatedStaff staff = securityUtils.getAuthStaff();
            dto.setStaff(new StaffInfoOutDto()
                    .setPosition(new PositionOutDto()
                            .setPosId(staff.getPosId())
                            .setName(staff.getPos()))
                    .setDepartment(new DepartmentMinOutDto()
                            .setDepId(staff.getDepId())
                            .setName(staff.getDep())));
        }
        return dto;
    }

}
