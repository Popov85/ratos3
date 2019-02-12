package ua.edu.ratos.service.dto.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class UserInDto {

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 2, max = 200, message = "{dto.string.invalid}")
    private String name;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 2, max = 200, message = "{dto.string.invalid}")
    private String surname;

    @Email(message = "{dto.email}")
    private String email;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 8, max = 100, message = "{dto.string.invalid}")
    private char[] password;
}
