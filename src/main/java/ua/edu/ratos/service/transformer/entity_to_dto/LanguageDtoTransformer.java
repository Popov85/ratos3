package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Language;
import ua.edu.ratos.service.dto.out.LanguageOutDto;

@Deprecated
@Component
public class LanguageDtoTransformer {

    public LanguageOutDto toDto(@NonNull final Language entity) {
        return new LanguageOutDto()
                .setLangId(entity.getLangId())
                .setName(entity.getName())
                .setAbbreviation(entity.getAbbreviation());
    }
}
