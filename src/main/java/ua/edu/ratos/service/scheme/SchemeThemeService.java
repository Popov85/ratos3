package ua.edu.ratos.service.scheme;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.domain.entity.SchemeTheme;
import ua.edu.ratos.domain.repository.SchemeRepository;
import ua.edu.ratos.domain.repository.SchemeThemeRepository;
import ua.edu.ratos.service.dto.entity.SchemeThemeInDto;
import ua.edu.ratos.service.dto.transformer.DtoSchemeThemeTransformer;

import java.util.Optional;

/**
 * Operations on SchemeTheme object:
 * 1) Add a new theme to the end (with default (all non-null) types known from the context);
 * 2) Remove an existing theme from the scheme (AJAX): load the List<SchemeTheme> of the given Scheme by SchemeId, and remove the currentIndex from it,
 *    save the list back;
 * 3) Edit a theme's content: get SchemeTheme by Id with type-settings associated (open a corresponding window) JS;
 * 4) The window: add (save) a new type (new window) for the current SchemeTheme (AJAX);
 * 5) The window: edit type settings (L1, L2, L3) AJAX;
 * 6) The window: deleted an existing type for the current SchemeTheme (AJAX);
 * 7) Re-order themes within a scheme by sending List<Long> and by updating each SchemeTheme with new order parameter.
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
     * Add a new theme to the end of Scheme's themes list with cascaded type-settings;
     * @param dto
     * @return generated SchemeTheme ID
     */
    @Transactional
    public Long save(@NonNull SchemeThemeInDto dto) {
        SchemeTheme schemeTheme = transformer.fromDto(dto);
        if (dto.isFirst()) {// update Scheme's 'completed' flag
            final Optional<Scheme> scheme = schemeRepository.findById(dto.getSchemeId());
            scheme.get().setCompleted(true);
        }
        return schemeThemeRepository.save(schemeTheme).getSchemeThemeId();
    }
}
