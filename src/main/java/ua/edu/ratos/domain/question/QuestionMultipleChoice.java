package ua.edu.ratos.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ua.edu.ratos.domain.answer.AnswerMultipleChoice;
import ua.edu.ratos.domain.answer.validator.AnswersMultipleChoiceListValidator;
import ua.edu.ratos.domain.answer.validator.AnswersMultipleChoiceListValidatorImpl;
import ua.edu.ratos.service.dto.ResponseMultipleChoice;

import static ua.edu.ratos.domain.question.QuestionMultipleChoice.Display.AUTO;

@Getter
@Setter
@ToString
public class QuestionMultipleChoice extends Question {

    public enum Display {AUTO, HIDE};
    /**
     * Should single-answer questions be displayed with radio-button, or checkboxes?
     */
    private Display display = AUTO;

    private List<AnswerMultipleChoice> answers;

    private transient boolean isSingle;
    /**
     * Creates a new empty Question object
     * @return newly created empty Question
     */
    public static QuestionMultipleChoice createEmpty() {
        QuestionMultipleChoice q = new QuestionMultipleChoice();
        q.setQuestion("");
        q.setLevel((byte)1);
        q.setAnswers(new ArrayList<>());
        return q;
    }
    /**
     * Checks whether this question contains only one correct answer or multiple correct answers
     * @return
     */
    public boolean isSingle() {
        if (!isValid()) throw new RuntimeException("Invalid question");
        int counter = 0;
        for (AnswerMultipleChoice answer : answers) {
            if (!answer.isValid()) throw new RuntimeException("Invalid answer");
            if (answer.getPercent()!=0) counter++;
        }
       return (counter > 1) ? false : true;
    }

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answers == null) return false;
        if (this.answers.isEmpty()) return false;
        if (this.answers.size()<2) return false;
        AnswersMultipleChoiceListValidator validator = new AnswersMultipleChoiceListValidatorImpl();
        if (!validator.isValid(this.answers)) return false;
        return true;
    }

    public int evaluate(ResponseMultipleChoice response) {
        List<Long> zeroAnswers = getZeroAnswers();
        List<Long> ids = response.ids;
        for (Long id: ids) {
            if (zeroAnswers.contains(id)) return 0;
        }
        List<Long> requiredAnswers = getRequiredAnswers();
        for (Long requiredAnswer : requiredAnswers) {
            if(!this.answers.contains(requiredAnswer)) return 0;
        }
        int result = 0;
        for (Long id: ids) {
            for (AnswerMultipleChoice answer : this.answers) {
                if (id==(answer.getAnswerId()))
                    result+=answer.getPercent();
            }
        }
        return result;
    }

    private List<Long> getZeroAnswers() {
        List<Long> zeroAnswers = this.answers
                .stream()
                .filter(answer -> answer.getPercent() == 0)
                .map(AnswerMultipleChoice::getAnswerId)
                .collect(Collectors.toList());
        return zeroAnswers;
    }

    private List<Long> getRequiredAnswers() {
        List<Long> requiredAnswers = new ArrayList<>();
        List<AnswerMultipleChoice> answers = this.answers;
        requiredAnswers.addAll(answers
                .stream()
                .filter(AnswerMultipleChoice::isRequired)
                .map(AnswerMultipleChoice::getAnswerId)
                .collect(Collectors.toList()));
        return requiredAnswers;
    }
}
