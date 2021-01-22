package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Deprecated
@Component
@AllArgsConstructor
public class DtoSchemeThemeSettingsTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public SchemeThemeSettings toEntity(@NonNull SchemeThemeSettingsInDto dto) {
        final SchemeThemeSettings s = modelMapper.map(dto, SchemeThemeSettings.class);
        if (dto.getSchemeThemeId()!=null) {
            s.setSchemeTheme(em.getReference(SchemeTheme.class, dto.getSchemeThemeId()));
        }
        s.setType(em.getReference(QuestionType.class, dto.getQuestionTypeId()));
        return s;
    }
}
