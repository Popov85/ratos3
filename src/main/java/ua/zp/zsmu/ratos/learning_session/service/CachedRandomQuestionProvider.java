package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.SchemeTheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.cache.CacheGuava;
import ua.zp.zsmu.ratos.learning_session.service.util.Shuffler;
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
        private CacheGuava cache;

        public Map<Theme, List<Question>> produceQuestionSequenceFromCache(Scheme scheme) {
                Map<Theme, Map<Integer, List<Question>>> cachedQuestions = cache.getCachedScheme(scheme);
                // Filter needed number of 1-st level, 2-level and 3-level questions
                Map<Theme, List<Question>> randomizedCachedQuestionSequences =
                        getRandomizedCachedQuestionSequences(cachedQuestions, scheme);
                return randomizedCachedQuestionSequences;
        }

        private Map<Theme,List<Question>> getRandomizedCachedQuestionSequences(Map<Theme, Map<Integer, List<Question>>> data, Scheme scheme) {
                Map<Theme, List<Question>> subCache = new HashMap<>();
                Map<Theme, Map<Integer, List<Question>>> allCache = data;
                List<SchemeTheme> themes = scheme.getThemes();
                for (SchemeTheme schemeTheme : themes) {
                        Theme theme = schemeTheme.getTheme();
                        List<Question> questions1StLevel = allCache.get(theme).get(1);
                        subCache.put(theme, Shuffler.shuffle(questions1StLevel, schemeTheme.getQuantityOf1stLevelQuestions()));
                        List<Question> questions2dLevel = allCache.get(theme).get(2);
                        if (!questions2dLevel.isEmpty()) {
                                subCache.put(theme, Shuffler.shuffle(questions2dLevel, schemeTheme.getQuantityOf2stLevelQuestions()));
                        }
                        List<Question> questions3dLevel = allCache.get(theme).get(3);
                        if (!questions3dLevel.isEmpty()) {
                                subCache.put(theme, Shuffler.shuffle(questions3dLevel, schemeTheme.getQuantityOf3stLevelQuestions()));
                        }
                }
                return subCache;
        }
}
