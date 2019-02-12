package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.HashSet;
import java.util.Set;

/**
 * It is used for students searching their own results
 * from their personal pages, search by {dep/course/scheme}
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultSearchSuiteSelfOutDto {

    Set<DepartmentMinOutDto> departments = new HashSet<>();

    Set<CourseMinOutDto> courses = new HashSet<>();

    Set<SchemeMinOutDto> schemes = new HashSet<>();

    public void addDep(DepartmentMinOutDto dto) {
        departments.add(dto);
    }

    public void addCourse(CourseMinOutDto dto) {
        courses.add(dto);
    }

    public void addScheme(SchemeMinOutDto dto) {
        schemes.add(dto);
    }
}
