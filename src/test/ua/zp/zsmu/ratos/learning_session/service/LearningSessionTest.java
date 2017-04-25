package ua.zp.zsmu.ratos.learning_session.service;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Andrey on 25.04.2017.
 */
public class LearningSessionTest {

        @Test
        public void isTimeOverTest() throws InterruptedException {
                Date startTime = new Date();
                // 1 min long session
                int duration = 1;
                Date expectedStopTime = DateUtils.addMinutes(startTime, duration);
                Date currentTime = new Date();
                boolean result = false;
                if (currentTime.compareTo(expectedStopTime)>=0) result=true;
                // Current time is 1 min less then expected end time
                Assert.assertFalse(result);
                Thread.sleep(61000L);
                currentTime = new Date();
                if (currentTime.compareTo(expectedStopTime)>=0) result=true;
                // Current time is 1s more then expected end time
                Assert.assertTrue(result);
        }
}
