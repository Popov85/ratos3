package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class GroupExtendedOutDto {

    private Long groupId;

    private String name;

    private LocalDateTime created;

    private StaffMinOutDto staff;

    private boolean enabled;

    // total number of students in the group
    private int students;
}
