package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.question.QuestionSequence;

public interface QuestionSQRepository extends JpaRepository<QuestionSequence, Long> {
}
