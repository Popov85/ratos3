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
public class UserInfoOutDto {

    private Long userId;

    private String name;

    private String surname;

    private String email;

    private String role;

    // Is working from inside LMS
    private boolean lms;

    // Nullable field, for staff only
    private StaffInfoOutDto staff;

}
