package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.service.dto.in.SettingsFBInDto;

public interface SettingsFBTransformer {

    SettingsFB toEntity(SettingsFBInDto dto);
}
