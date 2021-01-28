package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.grading.FourPointGrading;
import ua.edu.ratos.service.dto.out.grading.FourPointGradingOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface FourPointGradingMapper {

    FourPointGradingOutDto toDto(FourPointGrading entity);
}
