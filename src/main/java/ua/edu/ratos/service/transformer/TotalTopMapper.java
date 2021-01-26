package ua.edu.ratos.service.transformer;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.service.dto.out.game.AllTimesGamerOutDto;
import ua.edu.ratos.service.session.GameLabelResolver;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StudMinMapper.class})
public abstract class TotalTopMapper {

    @Autowired
    private GameLabelResolver gameLabelResolver;

    @Autowired
    private TotalAchievementsMapper totalAchievementsMapper;

    @Mapping(target = "student", source = "stud")
    public abstract AllTimesGamerOutDto toDto(Game entity);

    @AfterMapping
    void decorate(Game entity, @MappingTarget AllTimesGamerOutDto dto) {
        dto.setLabel(gameLabelResolver.getLabel(entity.getTotalWins()));
        dto.setTotalAchievements(totalAchievementsMapper.toDto(entity));
    }
}
