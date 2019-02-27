package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.service.dto.out.game.TotalAchievementsOutDto;

@Component
public class TotalAchievementsDtoTransformer {

    public TotalAchievementsOutDto toDto(@NonNull final Game entity) {
        return new TotalAchievementsOutDto()
                .setTotalPoints(entity.getTotalPoints())
                .setTotalBonuses(entity.getTotalBonuses())
                .setTotalWins(entity.getTotalWins());
    }

}
