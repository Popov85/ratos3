package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grading.TwoPointGrading;
import ua.edu.ratos.service.dto.out.grading.TwoPointGradingOutDto;

@Service
@AllArgsConstructor
public class TwoPointGradingDtoTransformer {

    private final StaffMinDtoTransformer staffMinDtoTransformer;

    public TwoPointGradingOutDto toDto(@NonNull final TwoPointGrading entity) {
        return new TwoPointGradingOutDto()
                .setTwoId(entity.getTwoId())
                .setName(entity.getName())
                .setThreshold(entity.getThreshold())
                .setDefault(entity.isDefault())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()));
    }
}
