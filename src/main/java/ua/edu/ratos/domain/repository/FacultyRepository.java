package ua.edu.ratos.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
