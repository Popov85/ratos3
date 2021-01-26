package ua.edu.ratos.service.transformer.entity_to_dto;

import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.service.dto.out.game.WeeklyAchievementsOutDto;

@Deprecated
@Component
public class WeeklyAchievementsDtoTransformer {

    public WeeklyAchievementsOutDto toDto(Week entity) {
        return new WeeklyAchievementsOutDto()
                .setWeekPoints(entity.getWeekPoints())
                .setWeekBonuses(entity.getWeekBonuses())
                .setWeekStrike(entity.getWeekStrike())
                .setWeekTimeSpent(entity.getWeekTimeSpent());
    }
}
