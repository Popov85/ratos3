package ua.edu.ratos.service.grading;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.dao.repository.SchemeThemeRepository;
import ua.edu.ratos.service.dto.in.SchemeThemeInDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSchemeThemeTransformer;

import java.util.Optional;

/**
 * Operations on SchemeTheme object:
 * 1) Add a new themeDomain to the end (with default (all non-null) types known from the context);
 * 2) Remove an existing themeDomain from the gradingDomain (AJAX): load the List<SchemeTheme> of the given SchemeDomain by SchemeId, and remove the currentIndex from it,
 *    save the list back;
 * 3) Edit a themeDomain's content: get SchemeTheme by Id with type-settingsDomain associated (open a corresponding window) JS;
 * 4) The window: addAnswer (save) a new type (new window) for the current SchemeTheme (AJAX);
 * 5) The window: edit type settingsDomain (L1, L2, L3) AJAX;
 * 6) The window: deleted an existing type for the current SchemeTheme (AJAX);
 * 7) Re-order themes within a gradingDomain by sending List<Long> and by updating each SchemeTheme with new order parameter.
 */
@Service
public class SchemeThemeService {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeThemeRepository schemeThemeRepository;

    @Autowired
    private DtoSchemeThemeTransformer transformer;

    /**
     * Add a new themeDomain to the end of SchemeDomain's themes list with cascaded type-settingsDomain;
     * @param dto
     * @return generated SchemeTheme ID
     */
    @Transactional
    public Long save(@NonNull SchemeThemeInDto dto) {
        SchemeTheme schemeTheme = transformer.toEntity(dto);
        if (dto.isFirst()) {// update SchemeDomain's 'completed' flag
            final Optional<Scheme> scheme = schemeRepository.findById(dto.getSchemeId());
            scheme.get().setCompleted(true);
        }
        return schemeThemeRepository.save(schemeTheme).getSchemeThemeId();
    }
}
