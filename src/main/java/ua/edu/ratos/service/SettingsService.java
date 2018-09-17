package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Settings;
import ua.edu.ratos.domain.repository.SettingsRepository;
import ua.edu.ratos.service.dto.entity.SettingsInDto;
import ua.edu.ratos.service.dto.transformer.DtoSettingsTransformer;
import java.util.List;
import java.util.Set;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private PropertiesService propertiesService;


    @Autowired
    private DtoSettingsTransformer transformer;

    @Transactional
    public Long save(@NonNull SettingsInDto dto) {
        return settingsRepository.save(transformer.fromDto(dto)).getSetId();
    }

    @Transactional
    public void update(@NonNull SettingsInDto dto) {
        if (dto.getSetId()==null || dto.getSetId()==0)
            throw new RuntimeException("Invalid ID");
        settingsRepository.save(transformer.fromDto(dto));
    }

    @Transactional(readOnly = true)
    public Set<Settings> findAllByStaffId(@NonNull Long staffId) {
        return settingsRepository.findAllByStaffId(staffId);
    }

    @Transactional(readOnly = true)
    public Set<Settings> findAllByStaffIdAndSettingsNameLettersContains(@NonNull Long staffId, @NonNull String contains) {
        return settingsRepository.findAllByStaffIdAndSettingsNameLettersContains(staffId, contains);
    }

    @Transactional(readOnly = true)
    public List<Settings> findAllByDepartmentId(@NonNull Long depId) {
        return settingsRepository.findByDepartmentId(depId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public Set<Settings> findAllByDepartmentIdAndSettingsNameLettersContains(@NonNull Long depId, @NonNull String contains) {
        return settingsRepository.findAllByDepartmentIdAndSettingsNameLettersContains(depId, contains);
    }

    @Transactional(readOnly = true)
    public List<Settings> findAll( @NonNull Pageable pageable) {
        return settingsRepository.findAll(pageable).getContent();
    }

    @Transactional
    public void deleteById(@NonNull Long setId) {
        settingsRepository.findById(setId).get().setDeleted(true);
    }

}
