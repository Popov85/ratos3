package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.service.dto.out.QuestionsParsingIssueOutDto;
import ua.edu.ratos.service.parsers.QuestionsParsingIssue;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuestionsParsingIssueMapper {

    QuestionsParsingIssueOutDto toDto(QuestionsParsingIssue parsingIssue);
}
