package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.service.dto.out.game.WeeklyGamerOutDto;

@Component
public class WeeklyGamerDtoTransformer {

    private StudMinDtoTransformer studMinDtoTransformer;

    private WeeklyAchievementsDtoTransformer weeklyAchievementsDtoTransformer;

    @Autowired
    public void setStudMinDtoTransformer(StudMinDtoTransformer studMinDtoTransformer) {
        this.studMinDtoTransformer = studMinDtoTransformer;
    }

    @Autowired
    public void setWeeklyAchievementsDtoTransformer(WeeklyAchievementsDtoTransformer weeklyAchievementsDtoTransformer) {
        this.weeklyAchievementsDtoTransformer = weeklyAchievementsDtoTransformer;
    }

    public WeeklyGamerOutDto toDto(@NonNull final Week entity) {
        return new WeeklyGamerOutDto()
                .setStudent(studMinDtoTransformer.toDto(entity.getStud()))
                .setWeeklyAchievements(weeklyAchievementsDtoTransformer.toDto(entity));
    }
}
