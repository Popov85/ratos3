package ua.edu.ratos.service.dto.out.criteria;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.DepartmentOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOfStudentForAdminOutDto {

    /**
     * Regular result
     */
    private ResultOfStudentForStaffOutDto result;

    /**
     * Only for admins who search through large tables
     */
    private DepartmentOutDto department;
}
