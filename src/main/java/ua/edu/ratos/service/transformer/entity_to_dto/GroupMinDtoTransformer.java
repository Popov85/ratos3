package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.out.GroupMinOutDto;

@Component
public class GroupMinDtoTransformer {

    public GroupMinOutDto toDto(@NonNull final Group entity) {
        return new GroupMinOutDto()
                .setGroupId(entity.getGroupId())
                .setName(entity.getName());
    }
}
