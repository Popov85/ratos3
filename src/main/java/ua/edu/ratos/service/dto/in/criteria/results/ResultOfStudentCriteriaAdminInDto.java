package ua.edu.ratos.service.dto.in.criteria.results;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOfStudentCriteriaAdminInDto extends ResultOfStudentCriteriaStaffInDto {

    @NotNull
    private Long depId;
}
