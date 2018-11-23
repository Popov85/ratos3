package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
