package ua.zp.zsmu.ratos.learning_session.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.service.dto.SchemeDTO;

import java.util.List;

/**
 * Created by Andrey on 31.03.2017.
 */

public interface SchemeDAO extends CrudRepository<Scheme, Long> {

        List<Scheme> findAll();

        @Query("SELECT s FROM Scheme s LEFT JOIN FETCH s.themes WHERE s.id=?1")
        Scheme findOneWithThemes(Long id);

        @Query("SELECT s FROM Scheme s LEFT JOIN FETCH s.themes")
        List<Scheme> findAllWithThemes();

        /*// Works fine but fails to retrieve themeId = 761 if EAGER
        @Query(value = "SELECT * FROM scheme WHERE enabled=1 and anonymous=1", nativeQuery = true)
        List<Scheme> findAllAvailable();*/

        // Works fine but fails to retrieve themeId = 761 if EAGER
        @Query("SELECT s FROM Scheme s WHERE s.isEnabled=true and s.isAvailableForAnonymousUser = true")
        List<Scheme> findAllAvailableSchemes();
}
