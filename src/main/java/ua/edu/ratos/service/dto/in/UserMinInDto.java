package ua.edu.ratos.service.dto.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UserMinInDto {

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 2, max = 200, message = "{dto.string.invalid}")
    private String name;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 2, max = 200, message = "{dto.string.invalid}")
    private String surname;

    @Email(message = "{dto.email}")
    private String email;
}
