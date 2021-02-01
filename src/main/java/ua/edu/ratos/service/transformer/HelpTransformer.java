package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.service.dto.in.HelpInDto;

public interface HelpTransformer {

    Help toEntity(HelpInDto dto);
}
