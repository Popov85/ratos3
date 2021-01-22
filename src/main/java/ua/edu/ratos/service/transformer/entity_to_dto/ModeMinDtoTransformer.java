package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.service.dto.out.ModeMinOutDto;
import ua.edu.ratos.service.dto.out.ModeOutDto;

@Deprecated
@Slf4j
@Component
public class ModeMinDtoTransformer {

    // TODO consider ObjectMapper?
    public ModeMinOutDto toDto(@NonNull final Mode entity) {
        return new ModeMinOutDto()
                .setModeId(entity.getModeId())
                .setName(entity.getName())
                .setHelpable(entity.isHelpable())
                .setPreservable(entity.isPreservable())
                .setPyramid(entity.isPyramid())
                .setReportable(entity.isReportable())
                .setPauseable(entity.isPauseable())
                .setRightAnswer(entity.isRightAnswer())
                .setSkipable(entity.isSkipable())
                .setStarrable(entity.isStarrable());
    }
}
