package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.dto.view.QuestionsParsingResultOutDto;
import ua.edu.ratos.service.parsers.QuestionsParsingIssue;
import ua.edu.ratos.service.parsers.QuestionsParsingResult;

@Component
public class QuestionsParsingResultDtoTransformer {

    public QuestionsParsingResultOutDto toDto(@NonNull QuestionsParsingResult parsingResult, boolean saved) {
        QuestionsParsingResultOutDto dto = new QuestionsParsingResultOutDto()
                .setCharset(parsingResult.getCharset())
                .setQuestions(parsingResult.questions())
                .setIssues(parsingResult.issues())
                .setMajorIssues((int)parsingResult.issuesOf(QuestionsParsingIssue.Severity.MAJOR))
                .setMediumIssues((int)parsingResult.issuesOf(QuestionsParsingIssue.Severity.MEDIUM))
                .setMinorIssues((int)parsingResult.issuesOf(QuestionsParsingIssue.Severity.MINOR))
                .setAllIssues(parsingResult.getIssues())
                .setSaved(saved);
        return dto;
    }
}
