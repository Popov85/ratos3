package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.dao.repository.SchemeThemeSettingsRepository;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSchemeThemeSettingsTransformer;
import java.util.Set;

@Service
public class SchemeThemeSettingsService {

    private SchemeThemeSettingsRepository schemeThemeSettingsRepository;

    private DtoSchemeThemeSettingsTransformer dtoSchemeThemeSettingsTransformer;

    @Autowired
    public void setSchemeThemeSettingsRepository(SchemeThemeSettingsRepository schemeThemeSettingsRepository) {
        this.schemeThemeSettingsRepository = schemeThemeSettingsRepository;
    }

    @Autowired
    public void setDtoSchemeThemeSettingsTransformer(DtoSchemeThemeSettingsTransformer dtoSchemeThemeSettingsTransformer) {
        this.dtoSchemeThemeSettingsTransformer = dtoSchemeThemeSettingsTransformer;
    }



    @Transactional
    public Long save(@NonNull final SchemeThemeSettingsInDto dto) {
        SchemeThemeSettings schemeThemeSettings = dtoSchemeThemeSettingsTransformer.toEntity(dto);
        return schemeThemeSettingsRepository.save(schemeThemeSettings).getSchemeThemeSettingsId();
    }

    @Transactional
    public void update(@NonNull final Long settingsId, @NonNull final SchemeThemeSettingsInDto dto) {
        if (!schemeThemeSettingsRepository.existsById(settingsId))
            throw new RuntimeException("Failed to update settings: ID does not exist");
        SchemeThemeSettings schemeThemeSettings = dtoSchemeThemeSettingsTransformer.toEntity(dto);
        schemeThemeSettingsRepository.save(schemeThemeSettings);
    }

    // Used for checking if we are going to remove the last SchemeThemeSettings (prohibited)
    Set<SchemeThemeSettings> findAllBySchemeThemeId(@NonNull final Long schemeThemeId) {
        return schemeThemeSettingsRepository.findAllBySchemeThemeId(schemeThemeId);
    }

    // Do not call directly, call rather via SchemeThemeService's removeSettings() method
    void remove(@NonNull final Long settingsId) {
        schemeThemeSettingsRepository.deleteById(settingsId);
    }
}
