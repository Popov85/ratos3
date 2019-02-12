package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class SettingsService {

    private static final String SETTINGS_NOT_FOUND = "The requested Settings not found, setId = ";

    private static final String DEFAULT_SETTINGS_CANNOT_BE_MODIFIED = "Default settings cannot be modified!";

    private SettingsRepository settingsRepository;

    private DtoSettingsTransformer dtoSettingsTransformer;

    private SettingsDtoTransformer settingsDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setSettingsRepository(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Autowired
    public void setDtoSettingsTransformer(DtoSettingsTransformer dtoSettingsTransformer) {
        this.dtoSettingsTransformer = dtoSettingsTransformer;
    }

    @Autowired
    public void setSettingsDtoTransformer(SettingsDtoTransformer settingsDtoTransformer) {
        this.settingsDtoTransformer = settingsDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //-----------------------------------------------------CRUD---------------------------------------------------------

    @Transactional
    public Long save(@NonNull final SettingsInDto dto) {
        return settingsRepository.save(dtoSettingsTransformer.toEntity(dto)).getSetId();
    }

    @Transactional
    public void update(@NonNull final SettingsInDto dto) {
        Settings settings = settingsRepository.findById(dto.getSetId())
                .orElseThrow(() -> new EntityNotFoundException(SETTINGS_NOT_FOUND + dto.getSetId()));
        if (settings.isDefault()) throw new RuntimeException(DEFAULT_SETTINGS_CANNOT_BE_MODIFIED );
        settings.setName(dto.getName());
        settings.setDaysKeepResultDetails(dto.getDaysKeepResultDetails());
        settings.setDisplayMark(dto.isDisplayMark());
        settings.setDisplayPercent(dto.isDisplayPercent());
        settings.setDisplayThemeResults(dto.isDisplayThemeResults());
        settings.setLevel2Coefficient(dto.getLevel2Coefficient());
        settings.setLevel3Coefficient(dto.getLevel3Coefficient());
        settings.setQuestionsPerSheet(dto.getQuestionsPerSheet());
        settings.setSecondsPerQuestion(dto.getSecondsPerQuestion());
        settings.setStrictControlTimePerQuestion(dto.isStrictControlTimePerQuestion());
    }

    @Transactional
    public void deleteById(@NonNull final Long setId) {
        Settings settings = settingsRepository.findById(setId).orElseThrow(() -> new EntityNotFoundException(SETTINGS_NOT_FOUND + setId));
        if (settings.isDefault()) throw new RuntimeException(DEFAULT_SETTINGS_CANNOT_BE_MODIFIED );
        settings.setDeleted(true);
    }

    //-------------------------------------------------One (for update)-------------------------------------------------

    @Transactional(readOnly = true)
    public SettingsOutDto findOneForEdit(@NonNull final Long setId) {
        return settingsDtoTransformer.toDto(settingsRepository.findOneForEdit(setId));
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
