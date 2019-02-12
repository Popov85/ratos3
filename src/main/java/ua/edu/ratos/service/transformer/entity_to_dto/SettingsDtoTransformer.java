package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.service.dto.out.SettingsOutDto;

@Slf4j
@Component
public class SettingsDtoTransformer {

    private StaffMinDtoTransformer staffMinDtoTransformer;

    @Autowired
    public void setStaffMinDtoTransformer(StaffMinDtoTransformer staffMinDtoTransformer) {
        this.staffMinDtoTransformer = staffMinDtoTransformer;
    }

    public SettingsOutDto toDto(@NonNull final Settings entity) {
        return new SettingsOutDto()
                .setSetId(entity.getSetId())
                .setName(entity.getName())
                .setDaysKeepResultDetails(entity.getDaysKeepResultDetails())
                .setDisplayMark(entity.isDisplayMark())
                .setDisplayPercent(entity.isDisplayPercent())
                .setDisplayThemeResults(entity.isDisplayThemeResults())
                .setLevel2Coefficient(entity.getLevel2Coefficient())
                .setLevel3Coefficient(entity.getLevel3Coefficient())
                .setQuestionsPerSheet(entity.getQuestionsPerSheet())
                .setSecondsPerQuestion(entity.getSecondsPerQuestion())
                .setStrictControlTimePerQuestion(entity.isStrictControlTimePerQuestion())
                .setStaff(staffMinDtoTransformer.toDto(entity.getStaff()));
    }
}
