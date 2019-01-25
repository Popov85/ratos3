package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grade.FourPointGrading;
import ua.edu.ratos.service.dto.out.grading.FourPointGradingOutDto;

@Component
public class FourPointGradingDtoTransformer {

    public FourPointGradingOutDto toDto(@NonNull final FourPointGrading entity) {
        return new FourPointGradingOutDto()
                .setFourId(entity.getFourId())
                .setName(entity.getName())
                .setThreshold3(entity.getThreshold3())
                .setThreshold4(entity.getThreshold4())
                .setThreshold5(entity.getThreshold5());
    }
}
