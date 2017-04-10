package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Session;

import java.util.Date;

/**
 * Created by Andrey on 4/8/2017.
 */
@Repository
public interface SessionDAO extends CrudRepository<Session, Long> {

        @Modifying
        @Query("update Session s set s.session = ?1, s.lastSerialisationTime = ?2 where s.id = ?3")
        void updateSessionInfoById(byte[] session, Date lastSerialisationTime, Long sid);

}
