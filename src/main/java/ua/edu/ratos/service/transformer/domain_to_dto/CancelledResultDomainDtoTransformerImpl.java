package ua.edu.ratos.service.transformer.domain_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;

@Service
@Qualifier("cancelled")
public class CancelledResultDomainDtoTransformerImpl implements ResultDomainDtoTransformer {

    @Override
    public ResultOutDto toDto(@NonNull final ResultDomain resultDomain) {
        String name = resultDomain.getUser().getName();
        String surname = resultDomain.getUser().getSurname();
        String user = name.concat(" ").concat(surname);
        String scheme = resultDomain.getScheme().getName();
        ResultOutDto dto = new ResultOutDto(user, scheme, resultDomain.isPassed());
        return dto;
    }
}
