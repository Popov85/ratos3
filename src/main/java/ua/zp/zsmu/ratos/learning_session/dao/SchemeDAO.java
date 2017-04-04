package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.repository.CrudRepository;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

import java.util.List;

/**
 * Created by Andrey on 31.03.2017.
 */

public interface SchemeDAO extends CrudRepository<Scheme, Long> {

        List<Scheme> findAll();
}
