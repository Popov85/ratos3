package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.service.dto.out.LMSOutDto;
import ua.edu.ratos.service.transformer.LTICredentialsMapper;
import ua.edu.ratos.service.transformer.LTIVersionMapper;

@Deprecated
@Component
@AllArgsConstructor
public class LMSDtoTransformer {

    private final LTICredentialsMapper ltiCredentialsMapper;

    private final LTIVersionMapper ltiVersionMapper;


    public LMSOutDto toDto(@NonNull final LMS entity) {
        return new LMSOutDto()
                .setLmsId(entity.getLmsId())
                .setName(entity.getName())
                .setCredentials(ltiCredentialsMapper.toDto(entity.getCredentials()))
                .setLtiVersion(ltiVersionMapper.toDto(entity.getLtiVersion()));
    }
}
