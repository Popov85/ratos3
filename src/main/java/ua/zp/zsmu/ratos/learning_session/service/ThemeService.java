package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.zp.zsmu.ratos.learning_session.dao.ThemeDAO;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJDBC;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJPA;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;

/**
 * Created by Andrey on 03.04.2017.
 */
public class ThemeService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(QuestionService.class);

        @Autowired
        private ThemeDAO themeDAO;

        @Autowired
        private ThemeDAOJDBC themeDAOJDBC;

        @Autowired
        private ThemeDAOJPA themeDAOJPA;

        public List<Theme> findAll() {
                LOGGER.info("findAll Themes: "+ themeDAO.findAll());
                return themeDAO.findAll();
        }

        public Theme findOne(Long id) {
                //LOGGER.info("findOne Theme: "+ themeDAO.findOne(id));
                return themeDAO.findOne(id);
        }

        public List<Theme> getThemeWithQuestions() {
                return themeDAO.getThemeWithQuestions();
        }

        public Theme findOneThemeWithQuestions(Long id) {
                return themeDAO.findOneThemeWithQuestions(id);
        }

        public List<Question> findQuestionsByTheme(Long id) {
                List<Question> questions = null;
                try {
                        questions = themeDAOJDBC.getAllQuestions(id);
                } catch (Exception e) {
                        LOGGER.error("ERRoR: "+e.getMessage());
                }
                return questions;
        }

        public Theme findOneThemeWithQuestionsJPA(Long id) {
                Theme theme = null;
                try {
                        theme = themeDAOJPA.findAllQuestions(id);
                } catch (Exception e) {
                        LOGGER.error("ERR: "+e.getMessage());
                }
                return theme;
        }
        /*public Set<Question> getThemeQuestions(Theme theme) {
                try {
                        Hibernate.initialize(theme.getQuestions());
                } catch (Exception e) {
                        LOGGER.error("ERROR "+e.getMessage());
                }
                return theme.getQuestions();
        }*/
}
