package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Wins;
import ua.edu.ratos.service.dto.out.game.WinnerOutDto;
import ua.edu.ratos.service.transformer.StudMinMapper;

@Component
public class WinnerDtoTransformer {

    private StudMinMapper studMinMapper;

    @Autowired
    public void setStudMinDtoTransformer(StudMinMapper studMinMapper) {
        this.studMinMapper = studMinMapper;
    }

    public WinnerOutDto toDto(@NonNull final Wins entity) {
        return new WinnerOutDto()
                .setStudent(studMinMapper.toDto(entity.getStudent()))
                .setPoints(entity.getWonPoints())
                .setBonuses(entity.getWonBonuses())
                .setTimeSpent(entity.getWonTimeSpent());
    }
}
