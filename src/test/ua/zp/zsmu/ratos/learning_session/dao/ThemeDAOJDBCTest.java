package ua.zp.zsmu.ratos.learning_session.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJDBC;

/**
 * Created by Andrey on 4/15/2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class ThemeDAOJDBCTest {

        @Autowired
        private ThemeDAOJDBC themeDAOJDBC;

        @Test
        public void getOneTheme() {
                System.out.println("Theme: "+themeDAOJDBC.getOneTheme(1l));
        }

}
