package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.zp.zsmu.ratos.learning_session.model.Result;
import java.util.List;

/**
 * Created by Andrey on 06.06.2017.
 */
public interface ResultDAO extends CrudRepository<Result, Long> {

        @Query(value = "SELECT r FROM Result r where testTime >= CURDATE() - INTERVAL 12 HOUR", nativeQuery = true)
        List<Result> findAll12();
}
