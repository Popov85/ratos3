package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.service.domain.ModeDomain;

@Slf4j
@Component
public class ModeDomainTransformer {

    public ModeDomain toDomain(@NonNull final Mode entity) {
        return new ModeDomain()
                .setModeId(entity.getModeId())
                .setName(entity.getName())
                .setHelpable(entity.isHelpable())
                .setPauseable(entity.isPauseable())
                .setPreservable(entity.isPreservable())
                .setPyramid(entity.isPyramid())
                .setReportable(entity.isReportable())
                .setResultDetails(entity.isResultDetails())
                .setRightAnswer(entity.isRightAnswer())
                .setSkipable(entity.isSkipable())
                .setStarrable(entity.isStarrable());
    }
}
