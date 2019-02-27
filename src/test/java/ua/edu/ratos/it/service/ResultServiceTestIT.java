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
import javax.persistence.PersistenceContext;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResultServiceTestIT {

    private static final String FIND_RESULT = "select r from Result r left join fetch r.resultTheme t left join fetch t.theme where r.resultId=:resultId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ResultService resultService;

    @MockBean
    private SessionData sessionData;

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
    public void saveTest() {
        when(sessionData.getSchemeDomain()).thenReturn(schemeDomain);
        when(sessionData.getUserId()).thenReturn(1L);
        when(sessionData.getProgressData()).thenReturn(progressData);

        ResultPerTheme perTheme0 = new ResultPerTheme(new ThemeDomain(1L, "ThemeDomain#1"), 5, 85.00d);
        ResultPerTheme perTheme1 = new ResultPerTheme(new ThemeDomain(2L, "ThemeDomain#2"), 5,  75.00d);
        ResultPerTheme perTheme2 = new ResultPerTheme(new ThemeDomain(3L, "ThemeDomain#3"), 5,  25.00d);
        ResultPerTheme perTheme3 = new ResultPerTheme(new ThemeDomain(4L, "ThemeDomain#4"), 5,  50.00d);

        ResultDomain resultDomain = new ResultDomain()
                .setUser(new UserDomain(1L, "Name", "Surname"))
                .setScheme(sessionData.getSchemeDomain())
                .setPassed(true)
                .setPercent(58.75)
                .setGrade(3)
                .setThemeResults(Arrays.asList(perTheme0, perTheme1, perTheme2, perTheme3));

        Long resultId = resultService.save(resultDomain);
        Assert.assertEquals(1L, resultId.longValue());
        final Result foundResult = (Result) em.createQuery(FIND_RESULT).setParameter("resultId", 1L).getSingleResult();

        Assert.assertNotNull(foundResult);

        Assert.assertEquals(4, foundResult.getResultTheme().size());
    }
}
