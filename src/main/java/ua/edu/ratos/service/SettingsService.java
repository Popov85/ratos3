package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.dao.repository.SettingsRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SettingsInDto;
import ua.edu.ratos.service.dto.out.SettingsOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSettingsTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SettingsDtoTransformer;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SettingsService {

    private static final String ID_IS_NOT_INCLUDED = "SetId is not included, reject update!";
    private static final String DEFAULT_SETTINGS_CANNOT_BE_MODIFIED = "Default settings cannot be modified!";

    private final SettingsRepository settingsRepository;

    private final DtoSettingsTransformer dtoSettingsTransformer;

    private final SettingsDtoTransformer settingsDtoTransformer;

    private final SecurityUtils securityUtils;

    //-----------------------------------------------------CRUD---------------------------------------------------------
    @Transactional
    public Long save(@NonNull final SettingsInDto dto) {
        return settingsRepository.save(dtoSettingsTransformer.toEntity(dto)).getSetId();
    }

    // Not the "redundant save" anti-pattern!!
    // Exactly 2 queries: select, update + dynamic update(!)
    @Transactional
    public void update(@NonNull final SettingsInDto dto) {
        Long setId = dto.getSetId();
        if (setId == null || setId == 0)
            throw new RuntimeException(ID_IS_NOT_INCLUDED + setId);
        Settings settings = settingsRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException("Settings not found"+ setId));
        if (settings.isDefault())
            throw new RuntimeException(DEFAULT_SETTINGS_CANNOT_BE_MODIFIED );
        // Will merge actually
        settingsRepository.save(dtoSettingsTransformer.toEntity(dto));
    }

    @Transactional
    public void deleteById(@NonNull final Long setId) {
        Settings settings = settingsRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException("Settings not found" + setId));
        if (settings.isDefault()) throw new RuntimeException(DEFAULT_SETTINGS_CANNOT_BE_MODIFIED);
        settings.setDeleted(true);
    }

    //-------------------------------------------------One (for update)-------------------------------------------------
    @Transactional(readOnly = true)
    public SettingsOutDto findOneForEdit(@NonNull final Long setId) {
        return settingsDtoTransformer.toDto(settingsRepository.findOneForEdit(setId)
                .orElseThrow(()->new EntityNotFoundException("Settings is not found, setId = "+setId)));
    }

    //------------------------------------------------------Default-----------------------------------------------------
    @Transactional(readOnly = true)
    public Set<SettingsOutDto> findAllDefault() {
        return settingsRepository.findAllDefault()
                .stream()
                .map(settingsDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //---------------------------------------------------Staff table----------------------------------------------------
    @Transactional(readOnly = true)
    public Page<SettingsOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return settingsRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(settingsDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SettingsOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return settingsRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), contains, pageable).map(settingsDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SettingsOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return settingsRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(settingsDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SettingsOutDto> findAllByDepartmentIdAndSettingsNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return settingsRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), contains, pageable).map(settingsDtoTransformer::toDto);
    }

    //--------------------------------------------------------ADMIN-----------------------------------------------------
    @Transactional(readOnly = true)
    public Page<SettingsOutDto> findAll(@NonNull Pageable pageable) {
        return settingsRepository.findAll(pageable).map(settingsDtoTransformer::toDto);
    }

}
