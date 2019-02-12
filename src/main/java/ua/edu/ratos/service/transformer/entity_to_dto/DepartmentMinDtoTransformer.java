package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.service.dto.out.DepartmentMinOutDto;

@Component
public class DepartmentMinDtoTransformer {

    public DepartmentMinOutDto toDto(@NonNull final Department entity) {
        return new DepartmentMinOutDto()
                .setDepId(entity.getDepId())
                .setName(entity.getName());
    }
}
