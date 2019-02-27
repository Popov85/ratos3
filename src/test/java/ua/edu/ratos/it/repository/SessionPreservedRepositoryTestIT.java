package ua.edu.ratos.it.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.SessionPreserved;
import ua.edu.ratos.dao.repository.SessionPreservedRepository;
import ua.edu.ratos.it.ActiveProfile;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SessionPreservedRepositoryTestIT {

    @Autowired
    private SessionPreservedRepository sessionPreservedRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/preserved_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByUserIdTest() {
        Slice<SessionPreserved> result = sessionPreservedRepository.findAllByUserId(2L, PageRequest.of(0, 5));
        assertFalse(result.hasNext());
        assertEquals(5, result.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/preserved_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findCountUserIdTest() {
        long result = sessionPreservedRepository.countByUserId(3L);
        assertEquals(5, result);
    }

}
