package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {ResourceMinMapper.class, ThemeMinMapper.class})
public interface QuestionMinMapper {

    @Mapping(target = "type", source = "entity.type.typeId")
    @Mapping(target = "helpAvailable", expression = "java(entity.getHelp().isPresent())")
    QuestionSessionMinOutDto toDto(Question entity);
}
