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
@AllArgsConstructor
@ToString
public class Issue {
    /**
     * The severity of this parsing issue
     */
    public enum Severity {
        LOW, MEDIUM, HIGH
    }

    /**
     * The part of the file where faced with this issue
     */
    public enum Part {
        QUESTION, HEADER, ANSWER, HINT
    }

    private final String description;
    private final Severity severity;
    private final Part part;
    private final int row;
    private final String line;
}
