package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Options;
import ua.edu.ratos.service.dto.out.OptionsMinOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OptionsMinMapper {

    OptionsMinOutDto toDto(Options entity);
}
