package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.answer.*;
import ua.edu.ratos.service.dto.in.*;

public interface AnswerTransformer {

    AnswerMCQ toEntity(Long questionId, AnswerMCQInDto dto);

    AnswerMCQ toEntity(AnswerMCQInDto dto);

    AnswerFBSQ toEntity(AnswerFBSQInDto dto);

    AnswerFBMQ toEntity(Long questionId, AnswerFBMQInDto dto);

    AnswerFBMQ toEntity(AnswerFBMQInDto dto);

    AnswerMQ toEntity(Long questionId, AnswerMQInDto dto);

    AnswerMQ toEntity(AnswerMQInDto dto);

    AnswerSQ toEntity(Long questionId, AnswerSQInDto dto);

    AnswerSQ toEntity(AnswerSQInDto dto);
}
