package ua.edu.ratos.service.dto.in;

import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleByDepInDto {

    @Pattern(regexp = "2|3|4|5")
    private Long roleId;
}
