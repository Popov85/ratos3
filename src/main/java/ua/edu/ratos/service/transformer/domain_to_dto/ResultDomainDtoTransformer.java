package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;

/**
 * Known implementations are:
 *
 * @see RegularResultDomainDtoTransformerImpl
 * @see CancelledResultDomainDtoTransformerImpl
 */
public interface ResultDomainDtoTransformer {
    /**
     * Transforms session results into a DTO.
     * Different implementations are expected for cancelled and regular sessions.
     * @param resultDomain ResultDomain
     * @return DTO
     */
    ResultOutDto toDto(@NonNull ResultDomain resultDomain);
}
