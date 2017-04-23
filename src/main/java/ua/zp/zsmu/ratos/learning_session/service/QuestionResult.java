package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import ua.zp.zsmu.ratos.learning_session.model.Answer;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Andrey on 4/17/2017.
 */
@Data
@Getter
public final class QuestionResult implements Serializable {

        private static final long serialVersionUID = -3135632710599257031L;

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(QuestionResult.class);

        private int result = 0;

        private final Question question;
        /**
         * List of Answer IDs a user returned
         */
        private final List<Long> answers;

        public QuestionResult(final Question question, final List<Long> answers) {
                this.question = question;
                this.answers = answers;
        }

        /**
         * 1) Check if a students answer list contains any zero-answer (if so - 0)
         * 2) Check if a students answer list contains all the required answers (if not - 0)
         * 3) Calculate a students score;
         */
        public int calculateResult() {
                List<Long> zeroAnswers = getZeroAnswers();
                for (Long answer : answers) {
                        if (zeroAnswers.contains(answer)) return 0;
                }

                List<Long> requiredAnswers = getRequiredAnswers();
                for (Long requiredAnswer : requiredAnswers) {
                        if(!answers.contains(requiredAnswer)) return 0;
                }

                int result = 0;
                for (Long userAnswer : answers) {
                        List<Answer> allAnswers = question.getAnswers();
                        for (Answer answer : allAnswers) {
                                if (userAnswer.equals(answer.getId())) result+=answer.getPercentage();
                        }
                }
                this.result = result;
                return result;
        }

        private List<Long> getZeroAnswers() {
                List<Long> zeroAnswers = question.getAnswers()
                        .stream()
                        .filter(answer -> answer.getPercentage() == 0)
                        .map(Answer::getId)
                        .collect(Collectors.toList());
                return zeroAnswers;
        }

        private List<Long> getRequiredAnswers() {
                List<Long> requiredAnswers = new ArrayList<>();
                List<Answer> answers = question.getAnswers();
                requiredAnswers.addAll(answers
                        .stream()
                        .filter(Answer::isRequired)
                        .map((Function<Answer, Long>) Answer::getId)
                        .collect(Collectors.toList()));
                return requiredAnswers;
        }
}
