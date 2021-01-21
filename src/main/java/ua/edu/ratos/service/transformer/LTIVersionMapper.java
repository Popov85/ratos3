package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.lms.LTIVersion;
import ua.edu.ratos.service.dto.out.LTIVersionOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LTIVersionMapper {

    LTIVersionOutDto toDto(LTIVersion entity);
}
