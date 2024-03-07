package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.service.dto.in.SettingsInDto;

public interface SettingsTransformer {

    Settings toEntity(SettingsInDto dto);
}
