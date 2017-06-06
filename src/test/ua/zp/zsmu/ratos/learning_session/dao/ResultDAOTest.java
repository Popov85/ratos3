package ua.zp.zsmu.ratos.learning_session.dao;

import ch.qos.logback.classic.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.zp.zsmu.ratos.learning_session.model.Result;
import ua.zp.zsmu.ratos.learning_session.service.SessionService;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 06.06.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class ResultDAOTest {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ResultDAOTest.class);

        @Autowired
        private ResultDAO resultDAO;

        // Launch after making sure there is some results today.
        @Test
        public void itShouldRetrieveResultsFor12Hours() {
                List<Result> results = resultDAO.findAll12();
                for (Result result : results) {
                      LOGGER.info(result.toString());
                }
                assertTrue(!results.isEmpty());
        }
}
