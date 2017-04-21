package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.dao.QuestionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.SchemeTheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 21.04.2017.
 */
@Service
public class DBQuestionProvider {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DBQuestionProvider.class);

        @Autowired
        private QuestionDAO questionDAO;

        /**
         * Accesses DB and extracts all existing questions on given theme (used with CacheGuava class)
         * @param scheme
         * @return full rated question sequences by themes
         */
        public Map<Theme, Map<Integer, List<Question>>> produceRatedQuestionSequencesFromDB(Scheme scheme) {
                Map<Theme, Map<Integer, List<Question>>> ratedQuestionSequences = new HashMap<>();
                List<SchemeTheme> themes = scheme.getThemes();
                for (SchemeTheme theme : themes) {
                        Theme nextTheme = theme.getTheme();
                        Map<Integer, List<Question>> nextThemeRatedQuestions = new HashMap<>();
                        // Fetch all existing questions on this given next Theme from DB
                        nextThemeRatedQuestions.put(1, questionDAO.findByThemeAndLevel(nextTheme, (short) 1));
                        if (theme.getQuantityOf2stLevelQuestions()!=0) {
                                nextThemeRatedQuestions.put(2, questionDAO.findByThemeAndLevel(nextTheme, (short) 2));
                        }
                        if (theme.getQuantityOf3stLevelQuestions()!=0) {
                                nextThemeRatedQuestions.put(3, questionDAO.findByThemeAndLevel(nextTheme, (short) 3));
                        }
                        ratedQuestionSequences.put(nextTheme, nextThemeRatedQuestions);
                }
                return ratedQuestionSequences;
        }

}
