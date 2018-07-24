package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;

public interface QuestionMCQRepository extends JpaRepository<QuestionMultipleChoice, Long> {
}
