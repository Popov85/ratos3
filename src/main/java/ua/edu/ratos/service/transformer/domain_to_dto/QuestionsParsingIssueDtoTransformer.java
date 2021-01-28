package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.dto.out.QuestionsParsingIssueOutDto;
import ua.edu.ratos.service.parsers.QuestionsParsingIssue;

@Deprecated
@Component
public class QuestionsParsingIssueDtoTransformer {

    public QuestionsParsingIssueOutDto toDto(@NonNull final QuestionsParsingIssue parsingIssue) {
        return new QuestionsParsingIssueOutDto()
                .setDescription(parsingIssue.getDescription())
                .setSeverity(parsingIssue.getSeverity())
                .setRow(parsingIssue.getRow());
    }
}
