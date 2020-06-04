package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;
import ua.edu.ratos.service.dto.out.grading.FreePointGradingOutDto;

@Component
@AllArgsConstructor
public class FreePointGradingDtoTransformer {

    private final StaffMinDtoTransformer staffMinDtoTransformer;

    public FreePointGradingOutDto toDto(@NonNull final FreePointGrading entity) {
        return new FreePointGradingOutDto()
                .setFreeId(entity.getFreeId())
                .setName(entity.getName())
                .setPassValue(entity.getPassValue())
                .setMinValue(entity.getMinValue())
                .setMaxValue(entity.getMaxValue())
                .setDefault(entity.isDefault())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()));
    }
}
