package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.util.List;

/**
 * Created by Andrey on 23.03.2017.
 */
@Repository
public interface QuestionDAO extends CrudRepository<Question, Long> {
        List<Question> findAll();
}
