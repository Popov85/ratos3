package ua.edu.ratos.service.parsers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Andrey on 1/31/2018.
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class QuestionsParsingIssue {
    /**
     * The severity of this parsing issue
     */
    public enum Severity {
        MINOR, MEDIUM, MAJOR
    }

    /**
     * The part of the file where faced with this issue
     */
    public enum Part {
        HEADER, QUESTION, ANSWER, HINT
    }

    private final String description;
    private final Severity severity;
    private final Part part;
    private final int row;
    private final String line;
}
