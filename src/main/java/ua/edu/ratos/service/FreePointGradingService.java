package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.grade.FreePointGrading;
import ua.edu.ratos.dao.repository.FreePointGradingRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.FreePointGradingInDto;
import ua.edu.ratos.service.dto.out.grading.FreePointGradingOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoFreePointGradingTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.FreePointGradingDtoTransformer;
import javax.persistence.EntityNotFoundException;

@Service
public class FreePointGradingService {

    private static final String FREE_NOT_FOUND = "The requested FreePointGrading not found, freeId = ";

    private static final String DEFAULT_GRADINGS_CANNOT_BE_MODIFIED = "The default FreePointGrading cannot be modified";
    
    private FreePointGradingRepository freePointGradingRepository;
    
    private FreePointGradingDtoTransformer freePointGradingDtoTransformer;
    
    private DtoFreePointGradingTransformer dtoFreePointGradingTransformer;
    
    private SecurityUtils securityUtils;

    @Autowired
    public void setFreePointGradingRepository(ua.edu.ratos.dao.repository.FreePointGradingRepository freePointGradingRepository) {
        this.freePointGradingRepository = freePointGradingRepository;
    }

    @Autowired
    public void setFreePointGradingDtoTransformer(ua.edu.ratos.service.transformer.entity_to_dto.FreePointGradingDtoTransformer freePointGradingDtoTransformer) {
        this.freePointGradingDtoTransformer = freePointGradingDtoTransformer;
    }

    @Autowired
    public void setDtoFreePointGradingTransformer(DtoFreePointGradingTransformer dtoFreePointGradingTransformer) {
        this.dtoFreePointGradingTransformer = dtoFreePointGradingTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //------------------------------------------------------CRUD--------------------------------------------------------

    @Transactional
    public Long save(@NonNull final FreePointGradingInDto dto) {
        return freePointGradingRepository.save(dtoFreePointGradingTransformer.toEntity(dto)).getFreeId();
    }

    @Transactional
    public void update(@NonNull final FreePointGradingInDto dto) {
        if (dto.getFreeId()==null) throw new RuntimeException("FreePointGrading's DTO must contain freeId to be updated");
        FreePointGrading entity = freePointGradingRepository.findById(dto.getFreeId()).orElseThrow(() -> new EntityNotFoundException(FREE_NOT_FOUND + dto.getFreeId()));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setName(dto.getName());
        entity.setMinValue(dto.getMinValue());
        entity.setPassValue(dto.getPassValue());
        entity.setMaxValue(dto.getMaxValue());
    }

    @Transactional
    public void deleteById(@NonNull final Long freeId) {
        FreePointGrading entity = freePointGradingRepository.findById(freeId).orElseThrow(() -> new EntityNotFoundException(FREE_NOT_FOUND + freeId));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setDeleted(true);
    }

    //--------------------------------------------------One (for update)------------------------------------------------

    @Transactional(readOnly = true)
    public FreePointGradingOutDto findOneForEdit(@NonNull final Long freeId) {
        return freePointGradingDtoTransformer.toDto(freePointGradingRepository.findOneForEdit(freeId));
    }

    //-----------------------------------------------------Staff table--------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<FreePointGradingOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return freePointGradingRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(freePointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<FreePointGradingOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return freePointGradingRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(freePointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<FreePointGradingOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return freePointGradingRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(freePointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<FreePointGradingOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return freePointGradingRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(freePointGradingDtoTransformer::toDto);
    }
}
