package ua.edu.ratos.service.transformer;

import ua.edu.ratos.service.dto.out.QuestionsParsingResultOutDto;
import ua.edu.ratos.service.parsers.QuestionsParsingResult;

public interface QuestionsParsingResultTransformer {

    QuestionsParsingResultOutDto toDto(QuestionsParsingResult parsingResult, boolean saved);
}
