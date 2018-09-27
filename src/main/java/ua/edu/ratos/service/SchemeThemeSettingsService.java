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

    @Transactional
    public Long save(@NonNull SchemeThemeSettingsInDto dto) {
        SchemeThemeSettings schemeThemeSettings = transformer.fromDto(dto);
        return schemeThemeSettingsRepository.save(schemeThemeSettings).getSchemeThemeSettingsId();
    }

    @Transactional
    public void update(@NonNull Long setId, @NonNull SchemeThemeSettingsInDto dto) {
        if (!schemeThemeSettingsRepository.existsById(setId))
            throw new RuntimeException("Failed to update scheme-theme settings: ID does not exist");
        SchemeThemeSettings schemeThemeSettings = transformer.fromDto(dto);
        schemeThemeSettingsRepository.save(schemeThemeSettings);
    }

    @Transactional
    public void deleteById(@NonNull Long SchemeThemeSettingsId) {
        schemeThemeSettingsRepository.deleteById(SchemeThemeSettingsId);
    }


/*-----------------------SELECT-------------------------*/


    @Transactional(readOnly = true)
    public Set<SchemeThemeSettings> findAllBySchemeThemeId(@NonNull Long schemeThemeId) {
        return schemeThemeSettingsRepository.findAllBySchemeThemeId(schemeThemeId);
    }
}
