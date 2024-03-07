package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.dto.out.SettingsFBOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class, LanguageMapper.class})
public interface SettingsFBMapper {

    SettingsFBOutDto toDto(SettingsFB entity);

    @Mapping(target = "lang", source = "entity.lang.abbreviation")
    SettingsFBDomain toDomain(SettingsFB entity);
}
