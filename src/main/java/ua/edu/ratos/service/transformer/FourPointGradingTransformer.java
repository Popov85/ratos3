package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.grading.FourPointGrading;
import ua.edu.ratos.service.dto.in.FourPointGradingInDto;

public interface FourPointGradingTransformer {

    FourPointGrading toEntity(FourPointGradingInDto dto);
}
