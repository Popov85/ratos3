package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.out.StaffOutDto;

@Component
public class StaffDtoTransformer {

    private PositionDtoTransformer positionDtoTransformer;

    private DepartmentMinDtoTransformer departmentMinDtoTransformer;

    private RoleDtoTransformer roleDtoTransformer;

    private UserDtoTransformer userDtoTransformer;

    @Autowired
    public void setPositionDtoTransformer(PositionDtoTransformer positionDtoTransformer) {
        this.positionDtoTransformer = positionDtoTransformer;
    }

    @Autowired
    public void setDepartmentMinDtoTransformer(DepartmentMinDtoTransformer departmentMinDtoTransformer) {
        this.departmentMinDtoTransformer = departmentMinDtoTransformer;
    }

    @Autowired
    public void setRoleDtoTransformer(RoleDtoTransformer roleDtoTransformer) {
        this.roleDtoTransformer = roleDtoTransformer;
    }

    @Autowired
    public void setUserDtoTransformer(UserDtoTransformer userDtoTransformer) {
        this.userDtoTransformer = userDtoTransformer;
    }

    public StaffOutDto toDto(@NonNull final Staff entity) {
        return new StaffOutDto()
                .setStaffId(entity.getStaffId())
                .setUser(userDtoTransformer.toDto(entity.getUser()))
                .setPosition(positionDtoTransformer.toDto(entity.getPosition()))
                .setDepartment(departmentMinDtoTransformer.toDto(entity.getDepartment()));
    }
}
