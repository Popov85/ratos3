package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.domain.entity.SchemeTheme;
import ua.edu.ratos.domain.entity.SchemeThemeSettings;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.question.QuestionType;
import ua.edu.ratos.service.dto.entity.SchemeThemeInDto;
import javax.persistence.EntityManager;

@Component
public class DtoSchemeThemeTransformer {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.MANDATORY)
    public SchemeTheme fromDto(@NonNull SchemeThemeInDto dto) {
        SchemeTheme schemeTheme = new SchemeTheme();
        schemeTheme.setScheme(em.getReference(Scheme.class, dto.getSchemeId()));
        schemeTheme.setTheme(em.getReference(Theme.class, dto.getThemeId()));
        schemeTheme.setOrder(dto.getOrder());
        dto.getSchemeThemeSettings().forEach(s-> {
            final SchemeThemeSettings set = modelMapper.map(s, SchemeThemeSettings.class);
            set.setType(em.getReference(QuestionType.class, s.getQuestionTypeId()));
            schemeTheme.addSchemeThemeSettings(set);
        });
        return schemeTheme;
    }
}
