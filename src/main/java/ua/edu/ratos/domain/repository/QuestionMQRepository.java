package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ratos.domain.entity.question.QuestionMatcher;

public interface QuestionMQRepository extends JpaRepository<QuestionMatcher, Long> {
}
