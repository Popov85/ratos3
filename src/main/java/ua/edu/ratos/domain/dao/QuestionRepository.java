package ua.edu.ratos.domain.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.edu.ratos.domain.model.question.Question;
import ua.edu.ratos.domain.model.question.QuestionMultipleChoice;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query("select q from QuestionMultipleChoice q where q.theme.themeId = :themeId")
    List<QuestionMultipleChoice> findAllQuestionMultipleChoiceByThemeId(@Param(value="themeId") Long themeId);


}
