package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;
import ua.edu.ratos.service.dto.out.grading.FreePointGradingOutDto;

@Component
public class FreePointGradingDtoTransformer {

    private StaffMinDtoTransformer staffMinDtoTransformer;

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinDtoTransformer staffMinDtoTransformer) {
        this.staffMinDtoTransformer = staffMinDtoTransformer;
    }

    public FreePointGradingOutDto toDto(@NonNull final FreePointGrading entity) {
        return new FreePointGradingOutDto()
                .setFreeId(entity.getFreeId())
                .setName(entity.getName())
                .setPassValue(entity.getPassValue())
                .setMinValue(entity.getMinValue())
                .setMaxValue(entity.getMaxValue())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()));
    }
}
