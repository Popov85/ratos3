package ua.zp.zsmu.ratos.learning_session.service.cache;

import org.springframework.stereotype.Component;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache of questions for better performance
 * Created by Andrey on 13.04.2017.
 */
@Component
public class Cache {

        //private Map<Scheme, Map<Theme, List<Question>>> cache = new ConcurrentHashMap<>();

        private Map<Scheme, Map<Theme, Map<Integer, List<Question>>>> cache = new ConcurrentHashMap<>();

        public Map<Theme, Map<Integer, List<Question>>> getCache(Scheme scheme) {
                return this.cache.get(scheme);
        }

        /*public Map<Theme, List<Question>> getCache(Scheme scheme) {
                return this.cache.get(scheme);
        }*/

        public Map<Theme, Map<Integer, List<Question>>> addScheme(Scheme scheme, Map<Theme, Map<Integer, List<Question>>> themes) {
                return this.cache.putIfAbsent(scheme, themes);
        }

        // TODO reconsider: what if someone is reading data currently?
        /*public void removeScheme(Scheme scheme) {
                this.cache.remove(scheme);
        }*/

        // TODO reconsider: what if someone is reading data currently?
        /*public void emptyCache() {
                this.cache=new ConcurrentHashMap<>();
        }*/

        /**
         * TODO
         * Synchronises all the schemas with the database
         * Launch is a case some of the Questions were altered: deleted, updated, added
         */
        public void updateCache() {}

        /**
         * TODO
         * Synchronises the given schema with the database
         * @param schemeKey - a scheme to get synchronized with the database
         */
        public void updateCache(Long schemeKey) {}
}
