package ua.edu.ratos.service.transformer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.grading.TwoPointGrading;
import ua.edu.ratos.service.dto.out.grading.TwoPointGradingOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {StaffMinMapper.class})
public interface TwoPointGradingMapper {

    TwoPointGradingOutDto toDto(TwoPointGrading entity);
}
