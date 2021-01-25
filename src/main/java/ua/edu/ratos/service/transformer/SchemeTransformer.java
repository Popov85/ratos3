package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.dto.in.SchemeInDto;

public interface SchemeTransformer {

    Scheme toEntity(SchemeInDto dto);
}
