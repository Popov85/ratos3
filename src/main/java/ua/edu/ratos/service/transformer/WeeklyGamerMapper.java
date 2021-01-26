package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.service.dto.out.game.WeeklyGamerOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StudMinMapper.class, WeeklyAchievementsMapper.class})
public interface WeeklyGamerMapper {

    WeeklyGamerOutDto toDto(Week entity);
}
