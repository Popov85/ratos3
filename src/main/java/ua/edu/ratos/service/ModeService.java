package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.dao.repository.ModeRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.ModeInDto;
import ua.edu.ratos.service.dto.out.ModeOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoModeTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ModeDtoTransformer;
import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
public class ModeService {

    private static final String MODE_NOT_FOUND = "The requested Mode not found, modeId = ";

    private static final String DEFAULT_MODES_CANNOT_BE_MODIFIED = "Default modes cannot be modified!";

    private ModeRepository modeRepository;

    private DtoModeTransformer dtoModeTransformer;

    private ModeDtoTransformer modeDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setModeRepository(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    @Autowired
    public void setDtoModeTransformer(DtoModeTransformer dtoModeTransformer) {
        this.dtoModeTransformer = dtoModeTransformer;
    }

    @Autowired
    public void setModeDtoTransformer(ModeDtoTransformer modeDtoTransformer) {
        this.modeDtoTransformer = modeDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //----------------------------------------------------CRUD----------------------------------------------------------

    @Transactional
    public Long save(@NonNull final ModeInDto dto) {
        return modeRepository.save(dtoModeTransformer.toEntity(dto)).getModeId();
    }

    @Transactional
    public void update(@NonNull final ModeInDto dto) {
        if (dto.getModeId()==null) throw new RuntimeException("Mode's DTO must contain modeId to be updated");
        Mode mode = modeRepository.findById(dto.getModeId()).orElseThrow(() -> new EntityNotFoundException());
        if (mode.isDefaultMode()) throw new RuntimeException(DEFAULT_MODES_CANNOT_BE_MODIFIED);
        mode.setName(dto.getName());
        mode.setHelpable(dto.isHelpable());
        mode.setPauseable(dto.isPauseable());
        mode.setPreservable(dto.isPreservable());
        mode.setPyramid(dto.isPyramid());
        mode.setReportable(dto.isReportable());
        mode.setRightAnswer(dto.isRightAnswer());
        mode.setSkipable(dto.isSkipable());
        mode.setResultDetails(dto.isResultDetails());
        mode.setStarrable(dto.isStarrable());
    }

    @Transactional
    public void deleteById(@NonNull final Long modeId) {
        Mode mode = modeRepository.findById(modeId).orElseThrow(() -> new EntityNotFoundException(MODE_NOT_FOUND + modeId));
        if (mode.isDefaultMode()) throw new RuntimeException(DEFAULT_MODES_CANNOT_BE_MODIFIED);
        mode.setDeleted(true);
    }

    //-------------------------------------------------One (for edit)---------------------------------------------------

    @Transactional(readOnly = true)
    public ModeOutDto findOneForEdit(@NonNull final Long modeId) {
        return modeDtoTransformer.toDto(modeRepository.findOneForEdit(modeId));
    }

    //---------------------------------------------------Staff table----------------------------------------------------

    @Transactional(readOnly = true)
    public Page<ModeOutDto> findAllForTableByStaffId(@NonNull final Pageable pageable) {
        return modeRepository.findAllForTableByStaffId(securityUtils.getAuthStaffId(), pageable).map(modeDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ModeOutDto> findAllForTableByStaffIdAndModeNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return modeRepository.findAllForTableByStaffIdAndModeNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(modeDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ModeOutDto> findAllForTableByDepartmentId(@NonNull final Pageable pageable) {
        return modeRepository.findAllForTableByDepartmentId(securityUtils.getAuthDepId(), pageable).map(modeDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ModeOutDto> findAllForTableByDepartmentIdAndModeNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return modeRepository.findAllForTableByDepartmentIdAndModeNameLettersContains(securityUtils.getAuthDepId(), contains, pageable).map(modeDtoTransformer::toDto);
    }


    //--------------------------------------------------Staff drop-down-------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<ModeOutDto> findAllForDropDownByStaffIdAndModeNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return modeRepository.findAllForDropDownByStaffIdAndModeNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(modeDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<ModeOutDto> findAllForDropDownByDepartmentIdAndModeNameLettersContains(@NonNull final String contains, @NonNull final Pageable pageable) {
        return modeRepository.findAllForDropDownByDepartmentIdAndModeNameLettersContains(securityUtils.getAuthDepId(), contains, pageable).map(modeDtoTransformer::toDto);
    }

    //-------------------------------------------------------ADMIN------------------------------------------------------

    @Transactional(readOnly = true)
    public Page<ModeOutDto> findAll(@NonNull final Pageable pageable) {
        return modeRepository.findAll(pageable).map(modeDtoTransformer::toDto);
    }
}
