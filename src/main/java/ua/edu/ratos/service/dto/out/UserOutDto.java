package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class UserOutDto {

    private String name;

    private String surname;

    private String email;

    private boolean active;

    private Set<RoleOutDto> roles = new HashSet<>();
}
