package ua.edu.ratos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ua.edu.ratos.domain.entity.question.QuestionType;

public interface QuestionTypeRepository extends CrudRepository<QuestionType, Long> {
}
