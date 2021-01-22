package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.service.dto.out.ModeMinOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModeMinMapper {

    ModeMinOutDto toDto(Mode entity);
}
