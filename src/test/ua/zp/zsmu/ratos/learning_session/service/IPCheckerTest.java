package ua.zp.zsmu.ratos.learning_session.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.zp.zsmu.ratos.learning_session.dao.PersistenceContext;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

/**
 * Created by Andrey on 28.04.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class IPCheckerTest {

        private static final String REMOTE_IP = "192.168.70.140";

        private static final String[] SAMPLES_CORRECT = {
                "*",
                "192.168.70.140",
                "192.168.70.*"
        };

        private static final String[] SAMPLES_INCORRECT = {
                "*;class524",
                "192.168.70.141",
                ";;;;;-------;192.168.70.*"
        };

        @Autowired
        private IPChecker ipChecker;

        @Test(timeout = 1000)
        public void isSchemeAvailableFromIPAddressTest() throws Exception {
                String remoteIP = REMOTE_IP;
                for (String sample : SAMPLES_CORRECT) {
                        String availability = sample;
                        Scheme scheme = new Scheme();
                        scheme.setId(1L);
                        scheme.setMaskIPAddress(availability);
                        Assert.assertTrue(ipChecker.isSchemeAvailableFromIPAddress(scheme, remoteIP));
                }
        }

        @Test(timeout = 1000)
        public void isSchemeAvailableFromIPAddressTestIncorrectVariants() throws Exception {
                String remoteIP = REMOTE_IP;
                for (String sample : SAMPLES_INCORRECT) {
                        String availability = sample;
                        Scheme scheme = new Scheme();
                        scheme.setId(1L);
                        scheme.setMaskIPAddress(availability);
                        Assert.assertFalse(ipChecker.isSchemeAvailableFromIPAddress(scheme, remoteIP));
                }
        }

}
