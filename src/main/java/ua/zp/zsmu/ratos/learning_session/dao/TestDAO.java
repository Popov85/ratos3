package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.repository.CrudRepository;
import ua.zp.zsmu.ratos.learning_session.model.Test;

/**
 * Created by Andrey on 14.04.2017.
 */
public interface TestDAO extends CrudRepository<Test, Long> {

}
