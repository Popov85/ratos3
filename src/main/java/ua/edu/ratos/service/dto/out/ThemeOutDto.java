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
public class ThemeOutDto {

    private Long themeId;

    private String name;

    private LocalDateTime created;

    private CourseOutDto course;

    private StaffMinOutDto staff;

    private AccessOutDto access;
}
