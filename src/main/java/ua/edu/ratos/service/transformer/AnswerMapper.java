package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.answer.*;
import ua.edu.ratos.service.domain.answer.*;
import ua.edu.ratos.service.dto.out.answer.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SettingsFBMapper.class, PhraseMapper.class, ResourceMinMapper.class, ResourceMapper.class})
public interface AnswerMapper {

    AnswerFBMQOutDto toDto(AnswerFBMQ entity);

    AnswerFBSQOutDto toDto(AnswerFBSQ entity);

    AnswerMCQOutDto toDto(AnswerMCQ entity);

    AnswerMQOutDto toDto(AnswerMQ entity);

    AnswerSQOutDto toDto(AnswerSQ entity);

    @Mapping(target = "acceptedPhraseDomains", source = "acceptedPhrases")
    AnswerFBMQDomain toDomain(AnswerFBMQ entity);

    @Mapping(target = "acceptedPhraseDomains", source = "acceptedPhrases")
    AnswerFBSQDomain toDomain(AnswerFBSQ entity);

    @Mapping(target = "resourceDomain", source = "resource")
    AnswerMCQDomain toDomain(AnswerMCQ entity);

    @Mapping(target = "leftPhraseDomain", source = "leftPhrase")
    @Mapping(target = "rightPhraseDomain", source = "rightPhrase")
    AnswerMQDomain toDomain(AnswerMQ entity);

    @Mapping(target = "phraseDomain", source = "phrase")
    AnswerSQDomain toDomain(AnswerSQ entity);
}
