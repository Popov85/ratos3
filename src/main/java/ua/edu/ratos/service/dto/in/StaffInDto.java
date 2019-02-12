package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class StaffInDto{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long staffId;

    private @Valid UserInDto user;

    @Positive(message = "{dto.fk.required}")
    private long positionId;
}
