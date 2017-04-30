package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Computer;

import java.util.List;

/**
 * Created by Andrey on 4/30/2017.
 */
@Repository
public interface ComputerDAO extends CrudRepository<Computer, Long> {

        @Deprecated
        List<Computer> findDistinctByIp(String ip);

        @Query(value = "SELECT class FROM COMP WHERE ip = ?1", nativeQuery = true)
        List<Integer> findAllClassRoomIDsByIP(String ip);

}
