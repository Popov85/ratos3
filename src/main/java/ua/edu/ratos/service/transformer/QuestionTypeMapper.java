package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.service.dto.out.QuestionTypeOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuestionTypeMapper {

    QuestionTypeOutDto toDto(QuestionType entity);

    default long toId(QuestionType value) {
        return value.getTypeId();
    }
}
