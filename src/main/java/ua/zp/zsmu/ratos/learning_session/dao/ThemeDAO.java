package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;

/**
 * Created by Andrey on 03.04.2017.
 */
public interface ThemeDAO extends CrudRepository<Theme, Long> {

        List<Theme> findAll();

        @Query("SELECT t.title FROM Theme t")
        List<String> findAllTitles();

        @Query("SELECT t FROM Theme t LEFT JOIN FETCH t.questions WHERE t.id=?1")
        Theme findOneWithQuestions(Long id);
}
