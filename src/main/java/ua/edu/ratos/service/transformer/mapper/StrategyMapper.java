package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Strategy;
import ua.edu.ratos.service.domain.StrategyDomain;
import ua.edu.ratos.service.dto.out.StrategyOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StrategyMapper {

    StrategyOutDto toDto(Strategy entity);

    StrategyDomain toDomain(Strategy entity);
}
