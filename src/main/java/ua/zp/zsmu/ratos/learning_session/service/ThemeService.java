package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.ThemeDAO;
import ua.zp.zsmu.ratos.learning_session.dao.impl.QuestionDAOJDBC;
import ua.zp.zsmu.ratos.learning_session.dao.impl.ThemeDAOJPA;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;

/**
 * Created by Andrey on 03.04.2017.
 */
@Transactional
public class ThemeService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ThemeService.class);

        @Autowired
        private ThemeDAO themeDAO;

        @Autowired
        private QuestionDAOJDBC questionDAOJDBC;

        @Autowired
        private ThemeDAOJPA themeDAOJPA;


        public List<Theme> findAll() {
                List<Theme> themes = null;
                try {
                        themes = themeDAO.findAll();
                        LOGGER.info("OK");
                } catch (Exception e) {
                        LOGGER.error("ERR: "+e.getMessage());
                }
                return themes;
        }

        public Theme findOne(Long id) {
                return themeDAO.findOne(id);
        }


        public Theme findOneWithQuestions(Long id) {
                Theme theme = themeDAO.findOneWithQuestions(id);
                try {
                        LOGGER.info("theme's questions: " + theme.getQuestions());
                } catch (Exception e) {
                        LOGGER.error("ERR: "+e.getMessage());
                }
                return theme;
        }

        public List<Question> findQuestionsByTheme(Long id) {
                List<Question> questions = null;
                try {
                        questions = questionDAOJDBC.getAllQuestions(id);
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

        public List<String> findAllThemeTitles() {
                List<String> titles = themeDAO.findAllTitles();
                LOGGER.info("All titles "+titles);
                return titles;
        }
}
