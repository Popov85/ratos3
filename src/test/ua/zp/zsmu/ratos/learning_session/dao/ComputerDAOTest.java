package ua.zp.zsmu.ratos.learning_session.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.zp.zsmu.ratos.learning_session.model.Computer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 4/30/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class ComputerDAOTest {

        private static final String REMOTE_IP = "192.168.1.140";

        @Autowired
        private ComputerDAO computerDAO;

        @Test
        public void testDAO() {
                /*List<Computer> computers = computerDAO.findDistinctByIp(REMOTE_IP);
                System.out.println("classRooms: "+computers);*/
                List<Integer> classRooms = computerDAO.findAllClassRoomIDsByIP(REMOTE_IP);
                Assert.assertTrue(classRooms.size()>0);
        }
}
