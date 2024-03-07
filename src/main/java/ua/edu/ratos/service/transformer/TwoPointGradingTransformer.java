package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.grading.TwoPointGrading;
import ua.edu.ratos.service.dto.in.TwoPointGradingInDto;

public interface TwoPointGradingTransformer {

    TwoPointGrading toEntity(TwoPointGradingInDto dto);
}
