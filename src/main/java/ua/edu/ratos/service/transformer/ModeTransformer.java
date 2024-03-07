package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.service.dto.in.ModeInDto;

public interface ModeTransformer {

    Mode toEntity(ModeInDto dto);
}
