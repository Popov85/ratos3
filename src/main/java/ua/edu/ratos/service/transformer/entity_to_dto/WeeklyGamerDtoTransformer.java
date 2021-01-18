package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.service.dto.out.game.WeeklyGamerOutDto;
import ua.edu.ratos.service.transformer.StudMinMapper;

@Component
public class WeeklyGamerDtoTransformer {

    private StudMinMapper studMinMapper;

    private WeeklyAchievementsDtoTransformer weeklyAchievementsDtoTransformer;

    @Autowired
    public void setStudMinDtoTransformer(StudMinMapper studMinMapper) {
        this.studMinMapper = studMinMapper;
    }

    @Autowired
    public void setWeeklyAchievementsDtoTransformer(WeeklyAchievementsDtoTransformer weeklyAchievementsDtoTransformer) {
        this.weeklyAchievementsDtoTransformer = weeklyAchievementsDtoTransformer;
    }

    public WeeklyGamerOutDto toDto(@NonNull final Week entity) {
        return new WeeklyGamerOutDto()
                .setStudent(studMinMapper.toDto(entity.getStud()))
                .setWeeklyAchievements(weeklyAchievementsDtoTransformer.toDto(entity));
    }
}
