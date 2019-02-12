package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.service.dto.out.GroupOutDto;

@Component
public class GroupDtoTransformer {

    private StaffMinDtoTransformer staffMinDtoTransformer;

    private StudDtoTransformer studDtoTransformer;

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinDtoTransformer staffMinDtoTransformer) {
        this.staffMinDtoTransformer = staffMinDtoTransformer;
    }

    @Autowired
    public void setStudMinDtoTransformer(StudDtoTransformer studMinDtoTransformer) {
        this.studDtoTransformer = studMinDtoTransformer;
    }


    public GroupOutDto toDto(@NonNull final Group entity) {
        return new GroupOutDto()
                .setGroupId(entity.getGroupId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setEnabled(entity.isEnabled())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()))
                .setStudents(studDtoTransformer.toDto(entity.getStudents()));
    }

}
