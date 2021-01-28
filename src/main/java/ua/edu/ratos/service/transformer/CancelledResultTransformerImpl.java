package ua.edu.ratos.service.transformer;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.transformer.ResultTransformer;

@Component
@Qualifier("cancelled")
public class CancelledResultTransformerImpl implements ResultTransformer {

    @Override
    public ResultOutDto toDto(@NonNull final ResultDomain r) {
        String name = r.getUser().getName();
        String surname = r.getUser().getSurname();
        String user = name.concat(" ").concat(surname);
        String scheme = r.getScheme().getName();
        ResultOutDto dto = new ResultOutDto(user, scheme, r.isTimeOuted(), r.isPassed());
        return dto;
    }
}
