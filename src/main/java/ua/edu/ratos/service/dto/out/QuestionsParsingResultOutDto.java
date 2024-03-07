package ua.edu.ratos.service.dto.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.question.QuestionMCQOutDto;

import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsParsingResultOutDto {
    private String charset;
    private int questions;
    private int invalid;
    private int issues;
    private int majorIssues;
    private int mediumIssues;
    private int minorIssues;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<QuestionsParsingIssueOutDto> allIssues;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<QuestionMCQOutDto> content;
    // Specifies if the questions were saved to DB
    private boolean saved;
}
