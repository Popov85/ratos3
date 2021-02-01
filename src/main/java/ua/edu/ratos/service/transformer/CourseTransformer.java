package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.service.dto.in.CourseInDto;
import ua.edu.ratos.service.dto.in.LMSCourseInDto;

public interface CourseTransformer {

    Course toEntity(CourseInDto dto);

    Course toLMSEntity(LMSCourseInDto dto);

    Course toEntity(Course course, CourseInDto dto);

    Course toLMSEntity(Course course, LMSCourseInDto dto);
}
