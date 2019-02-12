package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.edu.ratos.dao.entity.ResultOfStudent;

public interface ResultOfStudentRepository extends JpaRepository<ResultOfStudent, Long>, JpaSpecificationExecutor<ResultOfStudent> {
}
