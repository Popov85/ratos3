package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.out.StaffOutDto;
import ua.edu.ratos.service.transformer.DepartmentMapper;
import ua.edu.ratos.service.transformer.PositionMapper;
import ua.edu.ratos.service.transformer.UserMapper;

@Deprecated
@Component
@AllArgsConstructor
public class StaffDtoTransformer {

    private final PositionMapper positionMapper;

    private final DepartmentMapper departmentMapper;

    private final UserMapper userMapper;

    public StaffOutDto toDto(@NonNull final Staff entity) {
        return new StaffOutDto()
                .setStaffId(entity.getStaffId())
                .setUser(userMapper.toDto(entity.getUser()))
                .setPosition(positionMapper.toDto(entity.getPosition()))
                .setDepartment(departmentMapper.toDto(entity.getDepartment()));
    }
}
