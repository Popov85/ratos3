package ua.zp.zsmu.ratos.learning_session.service.cache;

import lombok.Getter;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 18.04.2017.
 */
@Getter
public class ThemeAndRatedQuestions {

        private Theme theme;

        /**
         * Key - the level, value - the list of questions of this level
         */
        private Map<Integer, List<Question>> ratedQuestions;

        public ThemeAndRatedQuestions() {}

        public ThemeAndRatedQuestions(Theme theme, Map<Integer, List<Question>> ratedQuestions) {
                this.theme = theme;
                this.ratedQuestions = ratedQuestions;
        }
}
