package ua.zp.zsmu.ratos.learning_session.service.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.DBQuestionProvider;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrey on 19.04.2017.
 */
@Service
public class CacheGuava {

        // The maximum number of keys in the cache
        private static final long MAX_SIZE = 10;
        private static final long TIME_LIMIT_HOURS = 12;

        @Autowired
        private DBQuestionProvider dbQuestionProvider;

        private final LoadingCache<Scheme, Map<Theme, Map<Integer, List<Question>>>> cache;

        public CacheGuava() {
                cache = CacheBuilder.newBuilder()
                        .maximumSize(MAX_SIZE)
                        .expireAfterWrite(TIME_LIMIT_HOURS, TimeUnit.HOURS)
                        .build(new CacheLoader<Scheme, Map<Theme, Map<Integer, List<Question>>>>() {
                                 @Override
                                 public Map<Theme, Map<Integer, List<Question>>> load(Scheme key) throws Exception {
                                         return dbQuestionProvider.produceRatedQuestionSequencesFromDB(key);
                                 }
                         });
        }

        public Map<Theme, Map<Integer, List<Question>>> getCachedScheme(Scheme scheme) {
                return cache.getUnchecked(scheme);
        }

        public void updateCachedScheme(Scheme key) {
               cache.refresh(key);
        }

        public void removeCachedScheme(Scheme scheme) {
                cache.asMap().remove(scheme);
        }
}
