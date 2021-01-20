package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.dto.in.*;

public interface QuestionTransformer {

    QuestionMCQ toEntity(QuestionMCQInDto dto);

    QuestionFBSQ toEntity(QuestionFBSQInDto dto);

    QuestionFBMQ toEntity(QuestionFBMQInDto dto);

    QuestionMQ toEntity(QuestionMQInDto dto);

    QuestionSQ toEntity(QuestionSQInDto dto);
}
