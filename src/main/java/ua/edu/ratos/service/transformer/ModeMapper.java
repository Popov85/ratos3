package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.service.domain.ModeDomain;
import ua.edu.ratos.service.dto.out.ModeOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface ModeMapper {

    ModeOutDto toDto(Mode entity);

    ModeDomain toDomain(Mode entity);
}
