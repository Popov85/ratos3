package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Clazz;
import ua.edu.ratos.service.dto.out.ClassMinOutDto;

@Component
public class ClassMinDtoTransformer {

    public ClassMinOutDto toDto(@NonNull final Clazz entity) {
        return new ClassMinOutDto()
                .setClassId(entity.getClassId())
                .setName(entity.getName());
    }
}
