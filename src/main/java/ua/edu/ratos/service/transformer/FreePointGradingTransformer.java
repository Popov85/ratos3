package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.grading.FreePointGrading;
import ua.edu.ratos.service.dto.in.FreePointGradingInDto;

public interface FreePointGradingTransformer {

    FreePointGrading toEntity(FreePointGradingInDto dto);
}
