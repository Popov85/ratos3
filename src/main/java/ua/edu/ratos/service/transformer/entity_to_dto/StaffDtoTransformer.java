package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.out.StaffOutDto;

@Component
@AllArgsConstructor
public class StaffDtoTransformer {

    private final PositionDtoTransformer positionDtoTransformer;

    private final DepartmentDtoTransformer departmentDtoTransformer;

    private final UserDtoTransformer userDtoTransformer;

    public StaffOutDto toDto(@NonNull final Staff entity) {
        return new StaffOutDto()
                .setStaffId(entity.getStaffId())
                .setUser(userDtoTransformer.toDto(entity.getUser()))
                .setPosition(positionDtoTransformer.toDto(entity.getPosition()))
                .setDepartment(departmentDtoTransformer.toDto(entity.getDepartment()));
    }
}
