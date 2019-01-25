package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.repository.SchemeThemeRepository;
import ua.edu.ratos.service.dto.in.SchemeThemeInDto;
import ua.edu.ratos.service.dto.out.SchemeThemeOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSchemeThemeTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SchemeThemeDtoTransformer;

import java.util.Set;

@Slf4j
@Service
public class SchemeThemeService {

    @Autowired
    private SchemeThemeRepository schemeThemeRepository;

    @Autowired
    private DtoSchemeThemeTransformer dtoSchemeThemeTransformer;

    @Autowired
    private SchemeThemeDtoTransformer schemeThemeDtoTransformer;

    @Autowired
    private SchemeThemeSettingsService schemeThemeSettingsService;

    @Transactional
    public Long save(@NonNull final Long schemeId, @NonNull final SchemeThemeInDto dto) {
        SchemeTheme schemeTheme = dtoSchemeThemeTransformer.toEntity(schemeId, dto);
        return schemeThemeRepository.save(schemeTheme).getSchemeThemeId();
    }

    // Settings manipulations, we manage it here cause it affects theme completeness
    @Transactional
    public void removeSettings(@NonNull final Long schemeThemeId, @NonNull final Long settingsId) {
        if (!hasMultipleSettings(schemeThemeId)) throw new RuntimeException("Cannot remove the last settings");
        schemeThemeSettingsService.remove(settingsId);
    }

    @Transactional(readOnly = true)
    public SchemeThemeOutDto findOne(@NonNull final Long schemeThemeId) {
        return schemeThemeDtoTransformer.toDto(schemeThemeRepository.findForDtoById(schemeThemeId));
    }

    private boolean hasMultipleSettings(@NonNull final Long schemeThemeId) {
        Set<SchemeThemeSettings> settings = schemeThemeSettingsService.findAllBySchemeThemeId(schemeThemeId);
        if (settings==null || settings.size()<=1) return false;
        return true;
    }

    // Used for checking if we are going to remove the last SchemeTheme (prohibited)
    Set<SchemeTheme> findAllBySchemeId(@NonNull final Long schemeId) {
        return schemeThemeRepository.findAllBySchemeId(schemeId);
    }

    // Do not call directly, call rather via SchemeService's removeTheme() method
    void remove(@NonNull final Long schemeThemeId) {
        schemeThemeRepository.deleteById(schemeThemeId);
    }

}
