package ua.edu.ratos.service.transformer;

import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;

public interface ResultTransformer {

    ResultOutDto toDto(ResultDomain domain);
}
