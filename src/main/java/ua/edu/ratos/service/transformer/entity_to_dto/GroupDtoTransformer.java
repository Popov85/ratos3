package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.out.GroupOutDto;

@Component
public class GroupDtoTransformer {

    public GroupOutDto toDto(@NonNull final Group entity) {
        return new GroupOutDto()
                .setGroupId(entity.getGroupId())
                .setName(entity.getName());
    }
}
