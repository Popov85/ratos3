package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.service.dto.out.game.TotalAchievementsOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TotalAchievementsMapper {

    TotalAchievementsOutDto toDto(Game entity);
}
