package ua.zp.zsmu.ratos.learning_session.service.cache;

import org.springframework.stereotype.Component;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read-only question repository for better performance
 * Created by Andrey on 13.04.2017.
 */
@Component
public class QuestionContainer {

        /**
         * Question holder: SchemeId - List<Question> for each Theme the scheme consists of
         */
        private Map<Integer, List<Question>> cache = new HashMap<>();

        public Map<Integer, List<Question>> getCache() {
                return this.cache;
        }

        public List<Question> getQuestions(Long schemeKey) {
                return this.cache.get(schemeKey);
        }

        public boolean containsScheme(Long schemeKey) {
                return this.cache.containsKey(schemeKey);
        }

        public void addScheme(Integer schemeKey, List<Question> questions) {
                this.cache.put(schemeKey, questions);
        }

        public void removeScheme(Integer schemeKey) {
                this.cache.remove(schemeKey);
        }

        public void emptyCache() {
                this.cache=new HashMap<>();
        }

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
