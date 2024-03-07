package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.service.dto.out.LMSMinOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LMSMinMapper {

    LMSMinOutDto toDto(LMS entity);
}
