package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.lms.LTICredentials;
import ua.edu.ratos.service.dto.out.LTICredentialsOutDto;

@Component
public class LTICredentialsDtoTransformer {

    public LTICredentialsOutDto toDto(@NonNull final LTICredentials entity) {
        return new LTICredentialsOutDto()
                .setCredId(entity.getCredId())
                .setKey(entity.getKey())
                .setSecret(entity.getSecret());
    }
}
