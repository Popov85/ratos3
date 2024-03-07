package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.security.AuthenticatedUser;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.security.lti.LISUser;
import ua.edu.ratos.security.lti.LTIUserConsumerCredentials;
import ua.edu.ratos.service.dto.out.*;

@Service
@AllArgsConstructor
public class InfoService {

    private final SecurityUtils securityUtils;

    public UserInfoOutDto getUserInfo() {

        // Is LMS-USER with LTIUserConsumerCredentials as Principal?
        if (securityUtils.isLmsUser()) {
            LTIUserConsumerCredentials principal
                    = securityUtils.getLmsUserAuthentication();
            LISUser user = principal.getUser()
                    .orElse(new LISUser("LMS", "user"));
            return new UserInfoOutDto()
                    .setUserId(principal.getUserId())
                    .setName(user.getName())
                    .setSurname(user.getSurname())
                    .setEmail(principal.getEmail()
                            .orElse("default@example.com"))
                    .setRole("LMS-USER")
                    .setLms(true);
        }

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
                    .setDepartment(new DepartmentOutDto()
                            .setDepId(staff.getDepId())
                            .setName(staff.getDep())
                            .setFaculty(new FacultyOutDto()
                                    .setFacId(staff.getFacId())
                                    .setName(staff.getFac())
                                    .setOrganisation(new OrganisationOutDto()
                                            .setOrgId(staff.getOrgId())
                                            .setName(staff.getOrg())))));
        }
        return dto;
    }

}
