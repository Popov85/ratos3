package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.service.dto.out.QuestionTypeOutDto;

@Deprecated
@Component
public class QuestionTypeDtoTransformer {

    public QuestionTypeOutDto toDto(@NonNull final QuestionType entity) {
        return new QuestionTypeOutDto()
                .setTypeId(entity.getTypeId())
                .setAbbreviation(entity.getAbbreviation())
                .setDescription(entity.getDescription());
    }
}
