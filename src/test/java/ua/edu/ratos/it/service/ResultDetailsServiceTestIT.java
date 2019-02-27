package ua.edu.ratos.it.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.ResultDetails;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.ResultDetailsService;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.transformer.entity_to_domain.QuestionDomainTransformer;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResultDetailsServiceTestIT {

    private static final String FIND_SCHEME= "select s from Scheme s join fetch s.strategy join fetch s.settings join fetch s.mode join fetch s.grading join fetch s.course join fetch s.staff where s.schemeId=:schemeId";
    private static final String FIND_MCQ = "select q from QuestionMCQ q join fetch q.answers a left join fetch a.resources join fetch q.theme join fetch q.type join fetch q.lang left join fetch q.helps h left join fetch h.resources left join fetch q.resources where q.questionId=:questionId";
    private static final String FIND_RESULT_DETAILS = "select r from ResultDetails r join fetch r.result where r.detailId=:resultId";

    @Autowired
    private ResultDetailsService resultDetailsService;

    @Autowired
    private QuestionDomainTransformer questionDomainTransformer;

    @Autowired
    private SchemeDomainTransformer schemeDomainTransformer;

    @Autowired
    private EntityManager em;

    private ua.edu.ratos.dao.entity.Scheme scheme;

    private SessionData sessionData;

    @Before
    public void init() {
        QuestionMCQ q0 = (QuestionMCQ) em.createQuery(FIND_MCQ).setParameter("questionId", 1L).getSingleResult();
        QuestionMCQ q1 = (QuestionMCQ) em.createQuery(FIND_MCQ).setParameter("questionId", 2L).getSingleResult();
        QuestionMCQ q2 = (QuestionMCQ) em.createQuery(FIND_MCQ).setParameter("questionId", 3L).getSingleResult();
        QuestionMCQ q3 = (QuestionMCQ) em.createQuery(FIND_MCQ).setParameter("questionId", 4L).getSingleResult();
        QuestionMCQ q4 = (QuestionMCQ) em.createQuery(FIND_MCQ).setParameter("questionId", 5L).getSingleResult();

        List<QuestionMCQ> sequence = Arrays.asList(q0, q1, q2, q3, q4);

        scheme = (ua.edu.ratos.dao.entity.Scheme) em.createQuery(FIND_SCHEME).setParameter("schemeId", 1L).getSingleResult();

        sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(schemeDomainTransformer.toDomain(scheme))
                .withIndividualSequence(sequence.stream().map(s->questionDomainTransformer.toDomain(s)).collect(Collectors.toList()))
                .withQuestionsPerBatch(2)
                .withSessionTimeout(LocalDateTime.MAX)
                .withPerQuestionTimeLimit(-1)
                .build();
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/result_details_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() {
        resultDetailsService.save(sessionData, 1L);
        final ResultDetails foundResultDetails = (ResultDetails) em.createQuery(FIND_RESULT_DETAILS)
                .setParameter("resultId", 1L)
                .getSingleResult();
        Assert.assertNotNull(foundResultDetails);
    }
}
