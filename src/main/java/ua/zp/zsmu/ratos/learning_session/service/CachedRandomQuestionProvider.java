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
import ua.zp.zsmu.ratos.learning_session.service.cache.Cache;
import ua.zp.zsmu.ratos.learning_session.service.util.Shuffler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 13.04.2017.
 */
@Service
public class CachedRandomQuestionProvider {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CachedRandomQuestionProvider.class);

        @Autowired
        private Cache cache;

        @Autowired
        private QuestionDAO questionDAO;

        /**
         * Look up this Scheme in the cache.
         * 1) if present - return List<Question>
         * 2) if not - request DB and put List<Question> to cache, then return it
         * @param scheme
         * @return
         */
        public Map<Theme, List<Question>> produceQuestionSequenceFromCache(Scheme scheme) {
                Map<Theme, Map<Integer, List<Question>>> data = getEntireCachedQuestionSequences(scheme);
                // Filter needed number of 1-st level, 2-level and 3-level questions
                Map<Theme, List<Question>> result = getRandomizedCachedQuestionSequences(data, scheme);
                return result;
        }

        private Map<Theme,List<Question>> getRandomizedCachedQuestionSequences(Map<Theme, Map<Integer, List<Question>>> data, Scheme scheme) {
                Map<Theme, List<Question>> subCache = new HashMap<>();
                Map<Theme, Map<Integer, List<Question>>> allCache = data;
                List<SchemeTheme> themes = scheme.getThemes();
                for (SchemeTheme theme : themes) {
                        subCache.put(theme.getTheme(), Shuffler.shuffle(allCache.get(theme).get(1), theme.getQuantityOf1stLevelQuestions()));
                        if (theme.getQuantityOf2stLevelQuestions()!=0) {
                                subCache.put(theme.getTheme(), Shuffler.shuffle(allCache.get(theme).get(2), theme.getQuantityOf2stLevelQuestions()));
                        }
                        if (theme.getQuantityOf3stLevelQuestions()!=0) {
                                subCache.put(theme.getTheme(), Shuffler.shuffle(allCache.get(theme).get(3), theme.getQuantityOf3stLevelQuestions()));
                        }
                }
                return subCache;
        }

        private Map<Theme, Map<Integer, List<Question>>> getEntireCachedQuestionSequences(Scheme scheme) {
                Map<Theme, Map<Integer, List<Question>>> result = cache.getCache(scheme);
                if(result == null) {
                        Map<Theme, Map<Integer, List<Question>>> putResult = cache.addScheme(scheme, fillCache(scheme));
                        if(putResult != null) {
                                result = putResult;
                        } else {
                                result = cache.getCache(scheme);
                        }
                }
                return result;
        }

        private Map<Theme, Map<Integer, List<Question>>> fillCache(Scheme scheme) {
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
