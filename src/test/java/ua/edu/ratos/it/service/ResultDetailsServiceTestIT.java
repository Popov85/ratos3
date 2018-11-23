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
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.ResultDetailsService;
import ua.edu.ratos.service.session.domain.SessionData;
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

    private static final String FIND_MCQ = "select q from QuestionMultipleChoice q join fetch q.answers a left join fetch a.resources join fetch q.theme join fetch q.type join fetch q.lang left join fetch q.help left join fetch q.resources where q.questionId=:questionId";

    private static final String FIND_RESULT_DETAILS = "select r from ResultDetails r join fetch r.result where r.detailId=:resultId";

    @Autowired
    private ResultDetailsService resultDetailsService;

    @Autowired
    private EntityManager em;

    private ua.edu.ratos.dao.entity.Scheme scheme;

    private SessionData sessionData;

    @Before
    public void init() {

        Question q0 = (QuestionMultipleChoice) em.createQuery(FIND_MCQ).setParameter("questionId", 1L).getSingleResult();
        Question q1 = (QuestionMultipleChoice) em.createQuery(FIND_MCQ).setParameter("questionId", 2L).getSingleResult();
        Question q2 = (QuestionMultipleChoice) em.createQuery(FIND_MCQ).setParameter("questionId", 3L).getSingleResult();
        Question q3 = (QuestionMultipleChoice) em.createQuery(FIND_MCQ).setParameter("questionId", 4L).getSingleResult();
        Question q4 = (QuestionMultipleChoice) em.createQuery(FIND_MCQ).setParameter("questionId", 5L).getSingleResult();

        List<Question> sequence = Arrays.asList(q0, q1, q2, q3, q4);

        scheme = (ua.edu.ratos.dao.entity.Scheme) em.createQuery(FIND_SCHEME).setParameter("schemeId", 1L).getSingleResult();

        sessionData = new SessionData.Builder()
                .withKey("D7C5E8BED7EDA2381E69126A40B3B22C")
                .forUser(1L)
                .takingScheme(scheme.toDomain())
                .withIndividualSequence(sequence.stream().map(s->s.toDomain()).collect(Collectors.toList()))
                .withQuestionsPerBatch(2)
                .withSessionTimeout(LocalDateTime.MAX)
                .withPerQuestionTimeLimit(-1)
                .build();
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/result_details_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {

        Long detailsId = resultDetailsService.save(sessionData, 1L);

        Assert.assertEquals(1L, detailsId.longValue());

        final ResultDetails foundResultDetails =
                (ResultDetails) em.createQuery(FIND_RESULT_DETAILS)
                        .setParameter("resultId", 1L)
                        .getSingleResult();

        Assert.assertNotNull(foundResultDetails);
    }
}
