package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.service.dto.out.game.AllTimesGamerOutDto;
import ua.edu.ratos.service.session.GameLabelResolver;
import ua.edu.ratos.service.transformer.StudMinMapper;

@Component
public class TotalTopDtoTransformer {

    private StudMinMapper studMinMapper;

    private GameLabelResolver gameLabelResolver;

    private TotalAchievementsDtoTransformer totalAchievementsDtoTransformer;

    @Autowired
    public void setStudMinDtoTransformer(StudMinMapper studMinMapper) {
        this.studMinMapper = studMinMapper;
    }

    @Autowired
    public void setGameLabelResolver(GameLabelResolver gameLabelResolver) {
        this.gameLabelResolver = gameLabelResolver;
    }

    @Autowired
    public void setTotalAchievementsDtoTransformer(TotalAchievementsDtoTransformer totalAchievementsDtoTransformer) {
        this.totalAchievementsDtoTransformer = totalAchievementsDtoTransformer;
    }

    public AllTimesGamerOutDto toDto(@NonNull final Game entity) {
        return new AllTimesGamerOutDto()
                .setStudent(studMinMapper.toDto(entity.getStud()))
                .setLabel(gameLabelResolver.getLabel(entity.getTotalWins()))
                .setTotalAchievements(totalAchievementsDtoTransformer.toDto(entity));
    }
}
