package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.game.Wins;
import ua.edu.ratos.service.dto.out.game.WinnerOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StudMinMapper.class})
public interface WinnerMapper {

    @Mapping(target = "points", source = "entity.wonPoints")
    @Mapping(target = "bonuses", source = "entity.wonBonuses")
    @Mapping(target = "timeSpent", source = "entity.wonTimeSpent")
    WinnerOutDto toDto(Wins entity);
}
