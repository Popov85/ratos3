package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;
import ua.edu.ratos.service.dto.out.grading.FreePointGradingOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface FreePointGradingMapper {

    FreePointGradingOutDto toDto(FreePointGrading entity);
}
