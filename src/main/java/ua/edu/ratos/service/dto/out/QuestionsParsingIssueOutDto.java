package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.parsers.QuestionsParsingIssue;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionsParsingIssueOutDto {

    private String description;
    private QuestionsParsingIssue.Severity severity;
    private int row;
}
