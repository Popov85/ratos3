package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.service.dto.out.PhraseOutDto;
import ua.edu.ratos.service.transformer.ResourceMapper;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Slf4j
@Component
public class PhraseDtoTransformer {

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

    public PhraseOutDto toDto(@NonNull final Phrase entity) {
        return new PhraseOutDto().setPhraseId(entity.getPhraseId())
                .setPhrase(entity.getPhrase())
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setLastUsed(entity.getLastUsed())
                .setResource((entity.getResource().isPresent()) ? resourceMapper.toDto(entity.getResource().get()) : null);
    }
}
