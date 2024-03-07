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
public class ResultOfStudentForReportOutDto extends ResultOfStudentForStaffOutDto {

    private DepartmentOutDto department;

}
