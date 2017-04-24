package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stepped builder of the whole session report
 * Updates after every question is answered
 * Not a pattern "builder"
 * Created by Andrey on 21.04.2017.
 */
final class ReportBuilder implements Serializable {

        private static final long serialVersionUID = -3447594255281334155L;

        private final Map<Theme, List<Question>> questionSequences;

        // Provides results of a requested question (qid)
        Map<Long, QuestionResult> questionResult = new HashMap<>();

        // Storage results by themes
        Map<Theme, List<QuestionResult>> resultSequences = new HashMap<>();

        Map<Question, QuestionStatistics> statistics = new HashMap<>();

        public ReportBuilder(Map<Theme, List<Question>> questionSequences) {
                this.questionSequences = questionSequences;
        }

        public void addResult(Theme theme, QuestionResult questionResult) {
                List<QuestionResult> results = resultSequences.get(theme);
                if (results==null) results = new ArrayList<>();
                results.add(questionResult);
                resultSequences.put(theme, results);
        }

        public void addStat(Question question, long timeTaken) {
                if (!statistics.containsKey(question)) {
                        QuestionStatistics questionStatistics = new QuestionStatistics(timeTaken);
                        statistics.put(question, questionStatistics);
                } else {
                        // update time and everything
                }
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
