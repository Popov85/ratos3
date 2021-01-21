package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.service.dto.in.LMSInDto;

public interface LMSTransformer {

    LMS toEntity(LMSInDto dto);

    LMS toEntity(LMS lms, LMSInDto dto);
}
