package ua.edu.ratos.service.parsers;

import lombok.*;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import java.util.List;
import java.util.stream.Collectors;


@Setter
@Getter
@ToString
public class QuestionsParsingResult {
    private final String header;
    private final List<QuestionMultipleChoice> questions;
    private final List<QuestionsParsingIssue> issues;
    /**
     * Number of filtered (invalid) questions
     */
    private int invalid;

    public QuestionsParsingResult(@NonNull String header, @NonNull List<QuestionMultipleChoice> questions, @NonNull List<QuestionsParsingIssue> issues) {
        this.header = header;
        this.questions = validate(questions);
        this.invalid = questions.size() - this.questions.size();
        this.issues = issues;
    }

    /**
     * Validates each answerIds first, validation includes decide if it is a single answerIds question
     * @param questions initial list of questions
     * @return filtered list of questions
     */
    private List<QuestionMultipleChoice> validate(List<QuestionMultipleChoice> questions) {
        return questions.stream().filter(q -> q.isValid()).collect(Collectors.toList());
    }

    public int invalid() {
        return this.invalid;
    }

    public int questions() {
        return this.questions.size();
    }

    public long questionsOf(boolean isSingle) {
        if (isSingle) {
            return questions.stream().filter(q -> q.isSingle()).count();
        } else {
            return questions.stream().filter(q -> !q.isSingle()).count();
        }
    }

    public int issues() {
        return this.issues.size();
    }

    public long issuesOf(QuestionsParsingIssue.Severity severity) {
        return issues.stream().filter(i -> i.getSeverity() == severity).count();
    }

    public long issuesOf(QuestionsParsingIssue.Part part) {
        return issues.stream().filter(i -> i.getPart() == part).count();
    }

}
