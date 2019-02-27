package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Wins;
import ua.edu.ratos.service.dto.out.game.WinnerOutDto;

@Component
public class WinnerDtoTransformer {

    private StudMinDtoTransformer studMinDtoTransformer;

    @Autowired
    public void setStudMinDtoTransformer(StudMinDtoTransformer studMinDtoTransformer) {
        this.studMinDtoTransformer = studMinDtoTransformer;
    }

    public WinnerOutDto toDto(@NonNull final Wins entity) {
        return new WinnerOutDto()
                .setStudent(studMinDtoTransformer.toDto(entity.getStudent()))
                .setPoints(entity.getWonPoints())
                .setBonuses(entity.getWonBonuses())
                .setTimeSpent(entity.getWonTimeSpent());
    }
}
