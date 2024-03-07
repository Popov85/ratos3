package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.dao.entity.game.Gamer;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.service.dto.out.StudMinOutDto;
import ua.edu.ratos.service.dto.out.game.GamerOutDto;
import ua.edu.ratos.service.session.GameLabelResolver;

import java.util.Optional;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMinMapper.class, ClassMinMapper.class, FacultyMinMapper.class, OrganisationMinMapper.class})
public abstract class GamerMapper {

    @Autowired
    private GameLabelResolver gameLabelResolver;

    @Autowired
    private TotalAchievementsMapper totalAchievementsMapper;

    @Autowired
    private WeeklyAchievementsMapper weeklyAchievementsMapper;

    public abstract GamerOutDto toDto(Gamer entity);

    public abstract StudMinOutDto toStudDto(Gamer entity);

    @AfterMapping
    void decorate(Gamer entity, @MappingTarget GamerOutDto dto) {
        dto.setStudent(toStudDto(entity));
        if (entity.getGame().isPresent()) {
            Game game = entity.getGame().get();
            dto.setLabel(gameLabelResolver.getLabel(game.getTotalWins()));
            dto.setTotal(totalAchievementsMapper.toDto(game));
        }
        if (entity.getWeek().isPresent()) {
            Optional<Week> week = entity.getWeek();
            dto.setWeekly(weeklyAchievementsMapper.toDto(week.get()));
        }
    }
}
