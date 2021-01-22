package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.out.SchemeInfoOutDto;
import ua.edu.ratos.service.transformer.ModeMinMapper;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class SchemeInfoDtoTransformer {

    private ModeMinMapper modeMinMapper;

    @Autowired
    public void setModeMinDtoTransformer(ModeMinMapper modeMinMapper) {
        this.modeMinMapper = modeMinMapper;
    }

    public SchemeInfoOutDto toDto(@NonNull final Scheme entity) {
        return new SchemeInfoOutDto()
                .setSchemeId(entity.getSchemeId())
                .setName(entity.getName())
                .setCourse(entity.getCourse().getName())
                .setStrategy(entity.getStrategy().getName())
                .setQuestions(this.getQuestions(entity.getThemes()))
                .setTimings(entity.getSettings().getSecondsPerQuestion())
                .setBatchTimeLimited(entity.getSettings().isStrictControlTimePerQuestion())
                .setMode(modeMinMapper.toDto(entity.getMode()))
                .setStaff(this.getStaff(entity.getStaff()));
    }


    private int getQuestions(List<SchemeTheme> themes) {
        int questions = 0;
        for (SchemeTheme theme : themes) {
            Set<SchemeThemeSettings> settings = theme.getSettings();
            for (SchemeThemeSettings s : settings) {
                short l1 = s.getLevel1();
                short l2 = s.getLevel2();
                short l3 = s.getLevel3();
                questions+=l1+l2+l3;
            }
        }
        return questions;
    }

    private String getStaff(Staff staff) {
        String position = staff.getPosition().getName();
        String name = staff.getUser().getName();
        String surname = staff.getUser().getSurname();
        return position+" "+name.charAt(0)+"."+surname;
    }
}
