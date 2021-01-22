package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Options;
import ua.edu.ratos.service.domain.OptionsDomain;
import ua.edu.ratos.service.dto.out.OptionsOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface OptionsMapper {

    OptionsOutDto toDto(Options entity);

    OptionsDomain toDomain(Options entity);
}
