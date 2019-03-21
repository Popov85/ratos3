package ua.edu.ratos.it.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.ResultDetails;
import ua.edu.ratos.dao.repository.ResultDetailsRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResultDetailsRepositoryTestIT {

    @Autowired
    private ResultDetailsRepository resultDetailsRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/result_details_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cleanExpiredTest() {
        LocalDateTime whenRemove = LocalDateTime.parse("2019-01-01T01:01:01");
        resultDetailsRepository.cleanExpired(whenRemove);
        List<ResultDetails> allLeft = resultDetailsRepository.findAll();
        assertEquals(7, allLeft.size());
    }
}
