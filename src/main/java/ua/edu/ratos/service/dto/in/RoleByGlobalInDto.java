package ua.edu.ratos.service.dto.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class RoleByGlobalInDto {

    @Pattern(regexp = "2|3|4|5|6|7")
    private Long roleId;
}
