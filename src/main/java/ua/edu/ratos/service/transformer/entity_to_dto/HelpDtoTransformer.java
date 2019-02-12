package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.service.dto.out.HelpOutDto;

@Slf4j
@Component
public class HelpDtoTransformer {

    private ResourceDtoTransformer resourceDtoTransformer;

    private StaffMinDtoTransformer staffMinDtoTransformer;

    @Autowired
    public void setResourceDtoTransformer(ResourceDtoTransformer resourceDtoTransformer) {
        this.resourceDtoTransformer = resourceDtoTransformer;
    }

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinDtoTransformer staffMinDtoTransformer) {
        this.staffMinDtoTransformer = staffMinDtoTransformer;
    }

    public HelpOutDto toDto(@NonNull final Help entity) {
        return new HelpOutDto()
                .setHelpId(entity.getHelpId())
                .setName(entity.getName())
                .setHelp(entity.getHelp())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()))
                .setResource((entity.getResource().isPresent()) ? resourceDtoTransformer.toDto(entity.getResource().get()) : null);
    }
}
