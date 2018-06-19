package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
