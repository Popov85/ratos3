package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Access;
import ua.edu.ratos.service.dto.out.AccessOutDto;

@Slf4j
@Component
public class AccessDtoTransformer {

    public AccessOutDto toDto(@NonNull final Access entity) {
        return new AccessOutDto()
                .setAccessId(entity.getAccessId())
                .setName(entity.getName());
    }
}
