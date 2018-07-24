package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankMultiple;

public interface QuestionFBMQRepository extends JpaRepository<QuestionFillBlankMultiple, Long> {
}
