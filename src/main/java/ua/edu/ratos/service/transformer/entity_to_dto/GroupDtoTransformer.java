package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.out.GroupOutDto;
import ua.edu.ratos.service.transformer.StaffMinMapper;
import ua.edu.ratos.service.transformer.StudMapper;

@Deprecated
@Component
public class GroupDtoTransformer {

    private StaffMinMapper staffMinMapper;

    private StudMapper studMapper;

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinMapper staffMinMapper) {
        this.staffMinMapper = staffMinMapper;
    }

    @Autowired
    public void setStudMinDtoTransformer(StudMapper studMapper) {
        this.studMapper = studMapper;
    }


    public GroupOutDto toDto(@NonNull final Group entity) {
        return new GroupOutDto()
                .setGroupId(entity.getGroupId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setEnabled(entity.isEnabled())
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setStudents(studMapper.toDto(entity.getStudents()));
    }

}
