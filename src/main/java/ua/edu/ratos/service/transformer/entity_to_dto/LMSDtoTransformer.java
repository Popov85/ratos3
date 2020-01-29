package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.service.dto.out.LMSOutDto;

@Component
@AllArgsConstructor
public class LMSDtoTransformer {

    private final LTICredentialsDtoTransformer ltiCredentialsDtoTransformer;

    private final LTIVersionDtoTransformer ltiVersionDtoTransformer;


    public LMSOutDto toDto(@NonNull final LMS entity) {
        return new LMSOutDto()
                .setLmsId(entity.getLmsId())
                .setName(entity.getName())
                .setCredentials(ltiCredentialsDtoTransformer.toDto(entity.getCredentials()))
                .setLtiVersion(ltiVersionDtoTransformer.toDto(entity.getLtiVersion()));
    }
}
