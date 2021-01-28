package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;

@Deprecated
public interface ResultDomainDtoTransformer {
    ResultOutDto toDto(@NonNull ResultDomain resultDomain);
}
