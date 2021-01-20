package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.service.dto.in.PhraseInDto;

public interface PhraseTransformer {

    Phrase toEntity(PhraseInDto dto);
}
