package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class GroupOutDto {

    private Long groupId;

    private String name;

    private LocalDateTime created;

    private StaffMinOutDto staff;

    private boolean enabled;

    private Set<StudOutDto> students;
}
