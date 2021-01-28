package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.service.domain.SettingsDomain;
import ua.edu.ratos.service.dto.out.SettingsOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface SettingsMapper {

    SettingsOutDto toDto(Settings entity);

    SettingsDomain toDomain(Settings entity);
}
