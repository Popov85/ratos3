package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.service.dto.out.ModeOutDto;

@Slf4j
@Component
@AllArgsConstructor
public class ModeDtoTransformer {

    private final StaffMinDtoTransformer staffMinDtoTransformer;

    public ModeOutDto toDto(@NonNull final Mode entity) {
        return new ModeOutDto()
                .setModeId(entity.getModeId())
                .setName(entity.getName())
                .setHelpable(entity.isHelpable())
                .setPreservable(entity.isPreservable())
                .setPyramid(entity.isPyramid())
                .setReportable(entity.isReportable())
                .setRightAnswer(entity.isRightAnswer())
                .setSkipable(entity.isSkipable())
                .setStarrable(entity.isStarrable())
                .setDefault(entity.isDefaultMode())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()));
    }
}
