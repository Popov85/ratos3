package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.zp.zsmu.ratos.learning_session.dao.PersistenceContext;
import ua.zp.zsmu.ratos.learning_session.service.dto.SchemeDTO;

import java.util.List;

/**
 * Created by Andrey on 4/29/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class SchemeServiceTest {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SchemeServiceTest.class);

        private static final String REMOTE_IP = "192.168.1.140";

        @Autowired
        private SchemeService schemeService;

        @Test(timeout = 1000)
        public void findAllAvailableFromIPAddressTest() throws Exception {
                List<SchemeDTO> availableSchemes = schemeService.findAllAvailableFromIPAddress(REMOTE_IP);
                LOGGER.info("Found: "+availableSchemes.size()+" schemes!");
                Assert.assertTrue(availableSchemes.size()>0);
        }
}
