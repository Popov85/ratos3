package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.service.dto.out.PhraseOutDto;

@Slf4j
@Component
public class PhraseDtoTransformer {

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

    public PhraseOutDto toDto(@NonNull final Phrase entity) {
        return new PhraseOutDto().setPhraseId(entity.getPhraseId())
                .setPhrase(entity.getPhrase())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()))
                .setLastUsed(entity.getLastUsed())
                .setResource((entity.getResource().isPresent()) ? resourceDtoTransformer.toDto(entity.getResource().get()) : null);
    }
}
