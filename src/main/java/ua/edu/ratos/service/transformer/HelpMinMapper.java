package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.service.dto.out.HelpMinOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {ResourceMinMapper.class})
public interface HelpMinMapper {

    HelpMinOutDto toDto(Help entity);
}
