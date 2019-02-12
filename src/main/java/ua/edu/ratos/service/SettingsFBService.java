package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.SettingsFBRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.SettingsFBInDto;
import ua.edu.ratos.service.dto.out.SettingsFBOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoSettingsFBTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.SettingsFBDtoTransformer;

@Service
public class SettingsFBService {

    private SettingsFBRepository settingsFBRepository;

    private DtoSettingsFBTransformer dtoSettingsFBTransformer;

    private SettingsFBDtoTransformer settingsFBDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setSettingsFBRepository(SettingsFBRepository settingsFBRepository) {
        this.settingsFBRepository = settingsFBRepository;
    }

    @Autowired
    public void setDtoSettingsFBTransformer(DtoSettingsFBTransformer dtoSettingsFBTransformer) {
        this.dtoSettingsFBTransformer = dtoSettingsFBTransformer;
    }

    @Autowired
    public void setSettingsFBDtoTransformer(SettingsFBDtoTransformer settingsFBDtoTransformer) {
        this.settingsFBDtoTransformer = settingsFBDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //--------------------------------------------------------CRUD------------------------------------------------------

    @Transactional
    public Long save(@NonNull final SettingsFBInDto dto) {
        return settingsFBRepository.save(dtoSettingsFBTransformer.toEntity(dto)).getSettingsId();
    }

    @Transactional
    public void update(@NonNull final SettingsFBInDto dto) {
        settingsFBRepository.save(dtoSettingsFBTransformer.toEntity(dto));
    }

    @Transactional
    public void deleteById(@NonNull final Long setId) {
        settingsFBRepository.findById(setId).get().setDeleted(true);
    }

    //---------------------------------------------------One (for update)-----------------------------------------------

    @Transactional(readOnly = true)
    public SettingsFBOutDto findOneForEdit(@NonNull final Long setId) {
        return settingsFBDtoTransformer.toDto(settingsFBRepository.findOneForEdit(setId));
    }

    //-----------------------------------------------------Staff table--------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<SettingsFBOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return settingsFBRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(settingsFBDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<SettingsFBOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return settingsFBRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), contains, pageable).map(settingsFBDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<SettingsFBOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return settingsFBRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(settingsFBDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<SettingsFBOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return settingsFBRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), contains, pageable).map(settingsFBDtoTransformer::toDto);
    }

    //-----------------------------------------------------------ADMIN--------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<SettingsFBOutDto> findAllAdmin(@NonNull final Pageable pageable) {
        return settingsFBRepository.findAllAdmin(pageable).map(settingsFBDtoTransformer::toDto);
    }
}
