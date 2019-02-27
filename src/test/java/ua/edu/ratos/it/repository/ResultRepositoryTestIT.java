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
import ua.edu.ratos.dao.entity.Result;
import ua.edu.ratos.dao.repository.ResultRepository;
import ua.edu.ratos.it.ActiveProfile;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResultRepositoryTestIT {

    @Autowired
    private ResultRepository resultRepository;


    //----------------------------------------------------TOP-1 for game------------------------------------------------
    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findFirstByUserIdAndSchemeIdTest() {
        Slice<Result> result = resultRepository.findFirstByUserIdAndSchemeId(3L, 5L, PageRequest.of(0, 1));
        assertTrue(result.hasContent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findFirstByUserIdAndSchemeIdNegativeTest() {
        Slice<Result> result = resultRepository.findFirstByUserIdAndSchemeId(22L, 99L, PageRequest.of(0, 1));
        assertFalse(result.hasContent());
    }
}
