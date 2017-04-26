package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Resource;

import java.util.List;

/**
 * Created by Andrey on 26.04.2017.
 */
@Repository
public interface ResourceDAO extends CrudRepository<Resource, Long> {

        @Query(value = "SELECT * FROM CONTROLS WHERE QID = ?1 ", nativeQuery = true)
        List<Resource> findResource(Long qid);
}
