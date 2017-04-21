package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 21.04.2017.
 */
class Report implements Serializable {

        private static final long serialVersionUID = -3447594255281334155L;

        private final Map<Theme, List<Question>> questionSequences;

        Map<Long, QuestionResult> questionResult = new HashMap<>();

        Map<Theme, List<QuestionResult>> resultSequences = new HashMap<>();

        Map<Question, QuestionStatistics> statistics = new HashMap<>();

        public Report(Map<Theme, List<Question>> questionSequences) {
                this.questionSequences = questionSequences;
        }

        public List<ThemeResult> getThemeReport() {
                List<ThemeResult> themeResults = new ArrayList<>();
                for (Map.Entry<Theme, List<QuestionResult>> themes : resultSequences.entrySet()) {
                        Theme theme = themes.getKey();
                        List<QuestionResult> results = themes.getValue();
                        int questionsInTheme = questionSequences.get(theme).size();
                        ThemeResult themeResult = new ThemeResult(theme, questionsInTheme, results);
                        themeResult.calculateResult();
                        themeResults.add(themeResult);
                }
                return themeResults;
        }
}
