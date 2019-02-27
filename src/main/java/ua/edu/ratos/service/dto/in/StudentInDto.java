package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class StudentInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long studId;

    private @Valid UserInDto user;

    @Min(value = 2000, message="{dto.year}")
    private int entranceYear;

    @Positive(message = "{dto.fk.required}")
    private long classId;

    @Positive(message = "{dto.fk.required}")
    private long facId;

    @Positive(message = "{dto.fk.required}")
    private long orgId;

}
