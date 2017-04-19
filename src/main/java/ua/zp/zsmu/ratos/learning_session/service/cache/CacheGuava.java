package ua.zp.zsmu.ratos.learning_session.service.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 19.04.2017.
 */
@Service
public class CacheGuava {

        // The maximum number of entries (Schemes) the cache may contain
        private static final long MAX_SIZE = 10;

        private final LoadingCache<Scheme, Map<Theme, Map<Integer, List<Question>>>> cache;

        public CacheGuava() {
                cache = CacheBuilder.newBuilder().maximumSize(MAX_SIZE).build(new CacheLoader<Scheme, Map<Theme, Map<Integer, List<Question>>>>() {
                         @Override
                         public Map<Theme, Map<Integer, List<Question>>> load(Scheme key) throws Exception {
                                 return downloadInitialData();
                         }
                 }
                );
        }

        private Map<Theme,Map<Integer,List<Question>>> downloadInitialData() {
                return new HashMap<>();
        }

        public Map<Theme, Map<Integer, List<Question>>> getScheme(Scheme scheme) {
                return cache.getUnchecked(scheme);
        }

}
