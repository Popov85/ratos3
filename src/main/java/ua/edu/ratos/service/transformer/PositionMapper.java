package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Position;
import ua.edu.ratos.service.dto.in.PositionInDto;
import ua.edu.ratos.service.dto.out.PositionOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {ReferenceMapper.class})
public interface PositionMapper {

    PositionOutDto toDto(Position entity);

    Position toEntity(PositionInDto dto);

    Position toEntity(Long id);
}
