package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.out.GroupExtendedOutDto;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Component
public class GroupExtendedDtoTransformer {

    private StaffMinMapper staffMinMapper;

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinMapper staffMinMapper) {
        this.staffMinMapper = staffMinMapper;
    }

    public GroupExtendedOutDto toDto(@NonNull final Group entity) {
        return new GroupExtendedOutDto()
                .setGroupId(entity.getGroupId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setEnabled(entity.isEnabled())
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setStudents(entity.getStudents().size());
    }
}
