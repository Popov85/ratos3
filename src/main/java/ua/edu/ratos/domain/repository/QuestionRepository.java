package ua.edu.ratos.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query("select q from QuestionMultipleChoice q where q.theme.themeId = :themeId")
    List<QuestionMultipleChoice> findAllQuestionMultipleChoiceByThemeId(@Param(value="themeId") Long themeId);

    @EntityGraph(value = "QuestionMultipleChoice", type = EntityGraph.EntityGraphType.LOAD)
    List<QuestionMultipleChoice> findAllQuestionMultipleChoiceWithAnswersByTheme(Theme theme);

    @Modifying
    @Query("update Question q set q.deleted = true where q.questionId = ?1")
    void pseudoDeleteById(Long questionId);

}
