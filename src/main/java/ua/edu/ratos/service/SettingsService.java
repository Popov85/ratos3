package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Settings;
import ua.edu.ratos.dao.repository.SettingsRepository;
import ua.edu.ratos.service.dto.in.SettingsInDto;
import ua.edu.ratos.service.dto.out.SettingsOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSettingsTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SettingsDtoTransformer;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private PropertiesService propertiesService;


    @Autowired
    private DtoSettingsTransformer dtoSettingsTransformer;

    @Autowired
    private SettingsDtoTransformer settingsDtoTransformer;

    @Transactional
    public Long save(@NonNull SettingsInDto dto) {
        return settingsRepository.save(dtoSettingsTransformer.toEntity(dto)).getSetId();
    }

    @Transactional
    public void update(@NonNull Long setId, @NonNull SettingsInDto dto) {
        if (!settingsRepository.existsById(setId))
            throw new RuntimeException("Failed to update Settings: ID does not exist");
        settingsRepository.save(dtoSettingsTransformer.toEntity(dto));
    }

    @Transactional
    public void deleteById(@NonNull Long setId) {
        settingsRepository.findById(setId).get().setDeleted(true);
    }

    //-------------------SELECT----------------------

    @Transactional(readOnly = true)
    public Page<SettingsOutDto> findAll(@NonNull Pageable pageable) {
        Page<Settings> page = settingsRepository.findAll(pageable);
        return new PageImpl<>(toDto(page.getContent()), pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<SettingsOutDto> findAllByStaffId(@NonNull Long staffId) {
        Set<Settings> content = settingsRepository.findAllByStaffId(staffId);
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<SettingsOutDto> findAllByStaffIdAndSettingsNameLettersContains(@NonNull Long staffId, @NonNull String contains) {
        Set<Settings> content = settingsRepository.findAllByStaffIdAndSettingsNameLettersContains(staffId, contains);
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<SettingsOutDto> findAllByDepartmentId(@NonNull Long depId) {
        List<Settings> content = settingsRepository.findByDepartmentId(depId, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<SettingsOutDto> findAllByDepartmentIdAndSettingsNameLettersContains(@NonNull Long depId, @NonNull String contains) {
        Set<Settings> content = settingsRepository.findAllByDepartmentIdAndSettingsNameLettersContains(depId, contains);
        return toDto(content);
    }

    private List<SettingsOutDto> toDto(@NonNull final Collection<Settings> content) {
        return content.stream().map(settingsDtoTransformer::toDto).collect(Collectors.toList());
    }

}
