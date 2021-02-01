package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.service.dto.out.game.WeeklyAchievementsOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WeeklyAchievementsMapper {

    WeeklyAchievementsOutDto toDto(Week entity);
}
