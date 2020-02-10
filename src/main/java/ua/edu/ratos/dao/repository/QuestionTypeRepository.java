package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ratos.dao.entity.QuestionType;

import java.util.Optional;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {

    @Query(value = "select t from QuestionType t where t.abbreviation = ?1")
    Optional<QuestionType> findTypeByAbbreviation(String abbreviation);
}
