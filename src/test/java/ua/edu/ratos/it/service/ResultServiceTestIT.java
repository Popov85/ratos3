package ua.edu.ratos.it.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Result;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.ResultService;
import ua.edu.ratos.service.domain.*;

import javax.persistence.EntityManager;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResultServiceTestIT {

    private static final String FIND_RESULT = "select r from Result r left join fetch r.resultTheme t join fetch t.theme where r.resultId=:resultId";

    @Autowired
    private ResultService resultService;

    @MockBean // @Mock works too
    private SessionData sessionData;

    @Autowired
    private EntityManager em;

    private SchemeDomain schemeDomain;

    private ProgressData progressData;

    @Before
    public void init() {
        schemeDomain = new SchemeDomain();
        schemeDomain.setSchemeId(1L);
        progressData = new ProgressData();
        progressData.setTimeSpent(12000L);
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/result_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        when(sessionData.getSchemeDomain()).thenReturn(schemeDomain);
        when(sessionData.getUserId()).thenReturn(1L);
        when(sessionData.getProgressData()).thenReturn(progressData);

        ResultPerTheme perTheme0 = new ResultPerTheme(1L, "ThemeDomain#1", 5, true, 85.00d, 5);
        ResultPerTheme perTheme1 = new ResultPerTheme(2L, "ThemeDomain#2", 5, true, 75.00d, 4);
        ResultPerTheme perTheme2 = new ResultPerTheme(3L, "ThemeDomain#3", 5, false, 25.00d, 2);
        ResultPerTheme perTheme3 = new ResultPerTheme(4L, "ThemeDomain#4", 5, true, 50.00d, 3);

        ResultDomain resultDomain = new ResultDomain("D7C5E8BED7EDA2381E69126A40B3B22C", true, 58.75, 3,
                Arrays.asList(perTheme0, perTheme1, perTheme2, perTheme3));
        Long resultId = resultService.save(sessionData, resultDomain, false);
        Assert.assertEquals(1L, resultId.longValue());
        final Result foundResult =
                (Result) em.createQuery(FIND_RESULT)
                        .setParameter("resultId", 1L)
                        .getSingleResult();

        Assert.assertNotNull(foundResult);

        Assert.assertEquals(4, foundResult.getResultTheme().size());
    }
}
