package ua.edu.ratos.dao.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
