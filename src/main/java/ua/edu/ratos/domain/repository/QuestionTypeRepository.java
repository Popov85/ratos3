package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.question.QuestionType;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
    QuestionType findByAbbreviation(String abbreviation);
}
