package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;

/**
 * Created by Andrey on 23.03.2017.
 */
@Repository
public interface QuestionDAO extends CrudRepository<Question, Long> {

        List<Question> findAll();

        List<Question> findByTheme(Theme theme);

        List<Question> findByThemeAndLevel(Theme theme, short level);

        @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answers WHERE q.id=?1")
        Question findOneQuestionWithAnswers(Long id);

        @Query(value = "SELECT * FROM QUEST WHERE THEME = ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
        List<Question> findNRandomByTheme(Long themeId, int quantity);

        @Query(value = "SELECT * FROM QUEST WHERE THEME = ?1 AND LEVEL=?2 ORDER BY RAND() LIMIT ?3", nativeQuery = true)
        List<Question> findNRandomByThemeAndLevel(Long themeId, int level, int quantity);
}
