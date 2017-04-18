package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.zp.zsmu.ratos.learning_session.dao.TestDAO;
import ua.zp.zsmu.ratos.learning_session.dao.ThemeDAO;
import ua.zp.zsmu.ratos.learning_session.model.Test;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.RandomQuestionProvider;

/**
 * Created by Andrey on 14.04.2017.
 */
@Controller
public class TestController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(TestController.class);

        @Autowired
        private ThemeDAO themeDAO;

        @GetMapping("/test")
        @ResponseBody
        private String start() {
                /*Theme test = new Theme();
                test.setTitle("Українсько - русский экскаватор");
                Theme savedTest = themeDAO.save(test);*/
                Theme dbTest = themeDAO.findOne(1411l);
                LOGGER.info("Theme: "+dbTest);
                return dbTest.toString();
        }
}
