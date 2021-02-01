package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.grading.Grading;
import ua.edu.ratos.service.domain.GradingDomain;
import ua.edu.ratos.service.dto.out.GradingOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GradingMapper {

    GradingOutDto toDto(Grading entity);

    GradingDomain toDomain(Grading entity);
}
