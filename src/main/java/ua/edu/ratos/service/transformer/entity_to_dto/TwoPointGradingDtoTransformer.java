package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grade.TwoPointGrading;
import ua.edu.ratos.service.dto.out.grading.TwoPointGradingOutDto;

@Service
public class TwoPointGradingDtoTransformer {

    public TwoPointGradingOutDto toDto(@NonNull final TwoPointGrading entity) {
        return new TwoPointGradingOutDto()
                .setTwoId(entity.getTwoId())
                .setName(entity.getName())
                .setThreshold(entity.getThreshold());
    }
}
