package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Session;

/**
 * Created by Andrey on 4/8/2017.
 */
@Repository
public interface SessionDAO extends CrudRepository<Session, Long> {

}
