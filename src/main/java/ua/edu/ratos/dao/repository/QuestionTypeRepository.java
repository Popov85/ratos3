package ua.edu.ratos.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.dao.entity.QuestionType;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
    QuestionType findByAbbreviation(String abbreviation);
}
