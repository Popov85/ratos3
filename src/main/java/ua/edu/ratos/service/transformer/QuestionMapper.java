package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.domain.question.*;
import ua.edu.ratos.service.dto.out.question.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AnswerMapper.class, QuestionTypeMapper.class, HelpMinMapper.class, HelpMapper.class, ResourceMapper.class, ThemeMapper.class})
public interface QuestionMapper {

    QuestionFBMQOutDto toDto(QuestionFBMQ entity);

    QuestionFBSQOutDto toDto(QuestionFBSQ entity);

    QuestionMCQOutDto toDto(QuestionMCQ entity);

    QuestionMQOutDto toDto(QuestionMQ entity);

    QuestionSQOutDto toDto(QuestionSQ entity);

    QuestionMinOutDto toDto(Question entity);

    @Mapping(target = "helpDomain", source = "help")
    @Mapping(target = "resourceDomains", source = "resources")
    @Mapping(target = "themeDomain", source = "theme")
    QuestionFBMQDomain toDomain(QuestionFBMQ entity);

    @Mapping(target = "helpDomain", source = "help")
    @Mapping(target = "resourceDomains", source = "resources")
    @Mapping(target = "themeDomain", source = "theme")
    QuestionFBSQDomain toDomain(QuestionFBSQ entity);

    @Mapping(target = "helpDomain", source = "help")
    @Mapping(target = "resourceDomains", source = "resources")
    @Mapping(target = "themeDomain", source = "theme")
    QuestionMCQDomain toDomain(QuestionMCQ entity);

    @Mapping(target = "helpDomain", source = "help")
    @Mapping(target = "resourceDomains", source = "resources")
    @Mapping(target = "themeDomain", source = "theme")
    QuestionMQDomain toDomain(QuestionMQ entity);

    @Mapping(target = "helpDomain", source = "help")
    @Mapping(target = "resourceDomains", source = "resources")
    @Mapping(target = "themeDomain", source = "theme")
    QuestionSQDomain toDomain(QuestionSQ entity);

    default QuestionDomain toDomain(Question entity) {
        if (entity.getType().getTypeId().equals(1L)) {
            return toDomain((QuestionMCQ) entity);
        } else if (entity.getType().getTypeId().equals(2L)) {
            return toDomain((QuestionFBSQ) entity);
        } else if (entity.getType().getTypeId().equals(3L)) {
            return toDomain((QuestionFBMQ) entity);
        } else if (entity.getType().getTypeId().equals(4L)) {
            return toDomain((QuestionMQ) entity);
        } else if (entity.getType().getTypeId().equals(5L)) {
            return toDomain((QuestionSQ) entity);
        } else throw new RuntimeException("Failed to transform Question to QuestionDomain");
    }
}
