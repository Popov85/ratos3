package ua.edu.ratos.it.service.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.QuestionGenerator;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.session.SessionDataService;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SessionDataServiceTestIT extends QuestionGenerator {

    // To eliminate absence of difference because of the fast test invocation
    private static final long LEEWAY = 5;

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeDomainTransformer schemeDomainTransformer;

    private SessionData sessionData;

    private BatchOutDto batchOutDto;

    @Before
    public void init() {
        // The test's sql script ExecutionPhase.BEFORE_TEST_METHOD is invoked before @Before
        Scheme scheme = schemeRepository.findForSessionById(1L);
        SchemeDomain schemeDomain = schemeDomainTransformer.toDomain(scheme);

        List<QuestionDomain> sequence = Arrays.asList(
                createMCQ(1L, "QuestionDomain Multiple Choice #1"),
                createFBSQ(2L, "QuestionDomain Fill Blank Single #2"),
                createFBMQ(3L, "QuestionDomain Fill Blank Multiple #3", true),
                createMQ(4L, "Matcher QuestionDomain #4", true),
                createSQ(5L, "Sequence question #5"));

        SessionData sessionData = new SessionData.Builder()
                .withKey("D845E8BGD7EDA2381E69126A40B3B99C")
                .forUser(1L)
                .takingScheme(schemeDomain)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(5)
                .withSessionTimeout(LocalDateTime.MAX)
                .withPerQuestionTimeLimit(60)
                .build();

        BatchOutDto batchOutDto = new BatchOutDto.Builder()
                .withQuestions(sequence.stream().map(q -> q.toDto()).collect(Collectors.toList()))
                .withBatchesLeft(0)
                .withQuestionsLeft(0)
                .withTimeLeft(-1)
                .withBatchTimeLimit(300)
                .inMode(schemeDomain.getModeDomain())
                .build();
        sessionData.setCurrentBatch(batchOutDto);

        this.batchOutDto = batchOutDto;

        this.sessionData = sessionData;
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session_data_service.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest()  {
        sessionDataService.update(sessionData, batchOutDto);
        assertEquals(5, this.sessionData.getCurrentIndex());
        assertTrue(this.sessionData.getCurrentBatch().isPresent());
        assertTrue(this.sessionData.getCurrentBatchIssued().isBefore(LocalDateTime.now().plusSeconds(LEEWAY)));
        // 300 sec for the batch
        assertTrue(this.sessionData.getCurrentBatchTimeOut().isBefore(LocalDateTime.now().plusSeconds(300+LEEWAY)));
        //log.debug("CurrentBatchTimeOut = {}", this.sessionData.getCurrentBatchTimeOut());
    }

}
