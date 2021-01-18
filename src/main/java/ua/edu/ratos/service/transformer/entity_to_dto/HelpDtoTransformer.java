package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.service.dto.out.HelpOutDto;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Slf4j
@Component
public class HelpDtoTransformer {

    private ResourceDtoTransformer resourceDtoTransformer;

    private StaffMinMapper staffMinMapper;

    @Autowired
    public void setResourceDtoTransformer(ResourceDtoTransformer resourceDtoTransformer) {
        this.resourceDtoTransformer = resourceDtoTransformer;
    }

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinMapper staffMinMapper) {
        this.staffMinMapper = staffMinMapper;
    }

    public HelpOutDto toDto(@NonNull final Help entity) {
        return new HelpOutDto()
                .setHelpId(entity.getHelpId())
                .setName(entity.getName())
                .setHelp(entity.getHelp())
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setResource((entity.getResource().isPresent()) ? resourceDtoTransformer.toDto(entity.getResource().get()) : null);
    }
}
