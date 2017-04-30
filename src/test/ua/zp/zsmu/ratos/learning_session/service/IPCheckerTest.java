package ua.zp.zsmu.ratos.learning_session.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.zp.zsmu.ratos.learning_session.dao.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 28.04.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class IPCheckerTest {

        // A computer from comp. class with id=10
        private static final String REMOTE_IP = "192.168.1.140";

        private static final String[] SAMPLES_CORRECT = {
                "*",
                "    *              ",
                "192.168.1.140",
                "192.168.1.*",
                "192.168.*",
                "192.*",
                "10;192.168.70.111",
                ";  192.168.1.140;  ",
                "   ; 192.167.* ; 192.168.2.*; 192.168.1.178 ; 12; 565664646; 192.168.1.140"
        };

        private static final String[] SAMPLES_INCORRECT = {
                ";class524",
                "192.168.70.141",
                "11;192.168.70.",
                "192.168.2.*",
                "192.168.1.139; 192.168.1.256",
                "   ; 192.167.* ; 192.168.2.*; 192.168.1.178 ; 12; 565664646; 192.168.0.140"
        };

        @Autowired
        private IPChecker ipChecker;

        @Test(timeout = 1000)
        public void isSchemeAvailableFromIPAddressTest() throws Exception {
                String remoteIP = REMOTE_IP;
                List<Integer> classRooms = new ArrayList<>();
                classRooms.add(10);
                for (String sample : SAMPLES_CORRECT) {
                        Assert.assertTrue(ipChecker.isSchemeAvailableFromIPAddress(sample, remoteIP, classRooms));
                }
        }

        @Test(timeout = 1000)
        public void isSchemeAvailableFromIPAddressTestIncorrectVariants() throws Exception {
                String remoteIP = REMOTE_IP;
                List<Integer> classRooms = new ArrayList<>();
                classRooms.add(10);
                for (String sample : SAMPLES_INCORRECT) {
                        Assert.assertFalse(ipChecker.isSchemeAvailableFromIPAddress(sample, remoteIP, classRooms));
                }
        }

}
