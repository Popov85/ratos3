package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DepartmentOutDto {

    private Long depId;

    private String name;

    private FacultyOutDto faculty;
}
