package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Options;
import ua.edu.ratos.service.dto.in.OptionsInDto;

public interface OptionsTransformer {

    Options toEntity(OptionsInDto dto);
}
