package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.lms.LTICredentials;
import ua.edu.ratos.service.dto.out.LTICredentialsOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LTICredentialsMapper {

    LTICredentialsOutDto toDto(LTICredentials entity);
}
