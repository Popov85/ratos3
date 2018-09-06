package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.SchemeThemeSettings;
import ua.edu.ratos.domain.repository.SchemeThemeSettingsRepository;
import ua.edu.ratos.service.dto.entity.SchemeThemeSettingsInDto;
import ua.edu.ratos.service.dto.transformer.DtoSchemeThemeSettingsTransformer;

import java.util.Set;

@Service
public class SchemeThemeSettingsService {

    @Autowired
    private SchemeThemeSettingsRepository schemeThemeSettingsRepository;

    @Autowired
    private DtoSchemeThemeSettingsTransformer transformer;

    /**
     * Add (save) a new type-settings for the current SchemeTheme;
     * @param dto
     * @return
     */
    @Transactional
    public Long save(@NonNull SchemeThemeSettingsInDto dto) {
        SchemeThemeSettings schemeThemeSettings = transformer.fromDto(dto);
        return schemeThemeSettingsRepository.save(schemeThemeSettings).getSchemeThemeSettingsId();
    }

    @Transactional(readOnly = true)
    public Set<SchemeThemeSettings> findAllBySchemeThemeId(@NonNull Long schemeThemeId) {
        return schemeThemeSettingsRepository.findAllBySchemeThemeId(schemeThemeId);
    }

    /**
     * Edit type-settings (L1, L2, L3); Not-existing question types/levels should be disallowed at frontend
     * @param dto
     */
    @Transactional
    public void update(@NonNull SchemeThemeSettingsInDto dto) {
        if (dto.getSchemeThemeSettingsId()==null || dto.getSchemeThemeSettingsId()==0)
            throw new RuntimeException("Invalid ID");
        SchemeThemeSettings schemeThemeSettings = transformer.fromDto(dto);
        schemeThemeSettingsRepository.save(schemeThemeSettings);
    }

    /**
     * Delete an existing type-settings for the current SchemeTheme
     * @param SchemeThemeSettingsId
     */
    @Transactional
    public void deleteById(@NonNull Long SchemeThemeSettingsId) {
        schemeThemeSettingsRepository.deleteById(SchemeThemeSettingsId);
    }


}
