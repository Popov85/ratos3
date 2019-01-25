package ua.edu.ratos.it.service.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.session.BatchBuilder;
import ua.edu.ratos.service.session.SessionDataBuilder;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BatchBuilderTestIT {

    @Autowired
    private SessionDataBuilder sessionDataBuilder;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private BatchBuilder batchBuilder;


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/batch_builder_data_five.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void build5Test()  {
        // We build a SessionData object with 5 different type questions, not from within LMS
        Scheme scheme = schemeRepository.findForSessionById(1L);
        //Assert.assertNotNull(scheme);
        SessionData sessionData = sessionDataBuilder.build("38CB1A2F36F6FC1B217D335D87D57376", 1L, scheme);
        // Now we build the first batch of one question out of 5 in total
        BatchOutDto batchOutDto = batchBuilder.build(sessionData);
        log.debug("BatchOutDto = {}", batchOutDto);
        Assert.assertEquals(1, batchOutDto.getBatch().size());
        Assert.assertEquals(1, batchOutDto.getBatchMap().size());
        Assert.assertNotNull(batchOutDto.getModeDomain());
        Assert.assertEquals(-1, batchOutDto.getBatchTimeLimit());
        // in sec
        Assert.assertTrue(batchOutDto.getTimeLeft()<300);
        Assert.assertEquals(4, batchOutDto.getQuestionsLeft());
        Assert.assertEquals(4, batchOutDto.getBatchesLeft());
        Assert.assertNull(batchOutDto.getPreviousBatchResult());
        //log.debug("BatchOutDto = {}", batchOutDto);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/batch_builder_data_fifty.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void build50Test()  {
        // We build a SessionData object with 25 questions (5 of each type), from within LMS
        Scheme scheme = schemeRepository.findForSessionById(1L);
        SessionData sessionData = sessionDataBuilder.build("38CB1A2F36F6FC1B217D335D87D57376", 1L, scheme, 1L);
        // Now we build the first batch of 5 question out of 25 in total
        BatchOutDto batchOutDto = batchBuilder.build(sessionData);
        Assert.assertEquals(5, batchOutDto.getBatch().size());
        Assert.assertEquals(5, batchOutDto.getBatchMap().size());
        Assert.assertNotNull(batchOutDto.getModeDomain());
        // 60*5
        Assert.assertEquals(300, batchOutDto.getBatchTimeLimit());
        // in sec
        Assert.assertTrue(batchOutDto.getTimeLeft()<(60*25));
        Assert.assertEquals(20, batchOutDto.getQuestionsLeft());
        Assert.assertEquals(4, batchOutDto.getBatchesLeft());
        Assert.assertNull(batchOutDto.getPreviousBatchResult());
        log.debug("BatchOutDto = {}", batchOutDto);
    }
}
