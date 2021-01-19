package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.service.dto.out.HelpOutDto;
import ua.edu.ratos.service.transformer.ResourceMapper;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Deprecated
@Slf4j
@Component
public class HelpDtoTransformer {

    private ResourceMapper resourceMapper;

    private StaffMinMapper staffMinMapper;

    @Autowired
    public void setResourceDtoTransformer(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
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
                .setResource((entity.getResource().isPresent()) ? resourceMapper.toDto(entity.getResource().get()) : null);
    }
}
