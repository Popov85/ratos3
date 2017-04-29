package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.ClassRoom;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.util.List;

/**
 * Created by Andrey on 28.04.2017.
 */
@Repository
public interface ClassRoomDAO extends CrudRepository<ClassRoom, Long> {

        @Query("SELECT cr FROM ClassRoom cr LEFT JOIN FETCH cr.computers")
        List<ClassRoom> findAllWithComputers();

        @Query("SELECT cr FROM ClassRoom cr LEFT JOIN FETCH cr.computers where cr.id=?1")
        ClassRoom findOneWithComputers(Long id);

}
