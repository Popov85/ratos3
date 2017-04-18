package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.zp.zsmu.ratos.learning_session.dao.QuestionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.SchemeTheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 13.04.2017.
 */
public class DBRandomQuestionProvider {
        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DBRandomQuestionProvider.class);

        @Autowired
        private QuestionDAO questionDAO;

        /**
         * Produces a map with key - Theme and value - lists of questions from the database that belong to the given Scheme
         * @param scheme
         * @return a randomized list of Questions on every Theme from DB
         */
        public Map<Theme, List<Question>> produceQuestionSequenceFromDB(Scheme scheme) {
                Map<Theme, List<Question>> questionSequences = new HashMap<>();
                List<SchemeTheme> themes = scheme.getThemes();
                for (SchemeTheme theme : themes) {
                        Theme nextTheme = theme.getTheme();
                        List<Question> nextThemeQuestions = new ArrayList<>();
                        // Fetch questions on this given next Theme from DB
                        nextThemeQuestions.addAll(questionDAO.findNRandomByThemeAndLevel(nextTheme.getId(), 1, theme.getQuantityOf1stLevelQuestions()));
                        if (theme.getQuantityOf2stLevelQuestions()!=0) nextThemeQuestions.addAll(questionDAO.findNRandomByThemeAndLevel(nextTheme.getId(), 2, theme.getQuantityOf2stLevelQuestions()));
                        if (theme.getQuantityOf3stLevelQuestions()!=0) nextThemeQuestions.addAll(questionDAO.findNRandomByThemeAndLevel(nextTheme.getId(), 3, theme.getQuantityOf3stLevelQuestions()));
                        questionSequences.put(nextTheme, nextThemeQuestions);
                }
                return questionSequences;
        }
}
