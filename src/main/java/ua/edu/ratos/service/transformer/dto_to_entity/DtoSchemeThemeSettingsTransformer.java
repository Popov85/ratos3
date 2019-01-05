package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;
import javax.persistence.EntityManager;

@Component
public class DtoSchemeThemeSettingsTransformer {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.MANDATORY)
    public SchemeThemeSettings toEntity(@NonNull SchemeThemeSettingsInDto dto) {
        final SchemeThemeSettings s = modelMapper.map(dto, SchemeThemeSettings.class);
        s.setSchemeTheme(em.getReference(SchemeTheme.class, dto.getSchemeThemeId()));
        s.setType(em.getReference(QuestionType.class, dto.getQuestionTypeId()));
        return s;
    }
}
