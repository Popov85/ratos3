package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
