package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.grading.TwoPointGrading;
import ua.edu.ratos.dao.repository.TwoPointGradingRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.TwoPointGradingInDto;
import ua.edu.ratos.service.dto.out.grading.TwoPointGradingOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoTwoPointGradingTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.TwoPointGradingDtoTransformer;

import javax.persistence.EntityNotFoundException;

@Service
public class TwoPointGradingService {

    private static final String TWO_NOT_FOUND = "The requested TwoPointGrading not found, twoId = ";

    private static final String DEFAULT_GRADINGS_CANNOT_BE_MODIFIED = "The default TwoPointGrading cannot be modified";

    private TwoPointGradingRepository twoPointGradingRepository;

    private TwoPointGradingDtoTransformer twoPointGradingDtoTransformer;

    private DtoTwoPointGradingTransformer dtoTwoPointGradingTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setTwoPointGradingRepository(TwoPointGradingRepository twoPointGradingRepository) {
        this.twoPointGradingRepository = twoPointGradingRepository;
    }

    @Autowired
    public void setTwoPointGradingDtoTransformer(TwoPointGradingDtoTransformer twoPointGradingDtoTransformer) {
        this.twoPointGradingDtoTransformer = twoPointGradingDtoTransformer;
    }

    @Autowired
    public void setDtoTwoPointGradingTransformer(DtoTwoPointGradingTransformer dtoTwoPointGradingTransformer) {
        this.dtoTwoPointGradingTransformer = dtoTwoPointGradingTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //--------------------------------CRUD-------------------------------

    @Transactional
    public Long save(@NonNull final TwoPointGradingInDto dto) {
        return twoPointGradingRepository.save(dtoTwoPointGradingTransformer.toEntity(dto)).getTwoId();
    }

    @Transactional
    public void update(@NonNull final TwoPointGradingInDto dto) {
        if (dto.getTwoId()==null) throw new RuntimeException("TwoPointGrading's DTO must contain twoId to be updated");
        TwoPointGrading entity = twoPointGradingRepository.findById(dto.getTwoId()).orElseThrow(() -> new EntityNotFoundException(TWO_NOT_FOUND + dto.getTwoId()));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setName(dto.getName());
        entity.setThreshold(dto.getThreshold());
    }

    @Transactional
    public void deleteById(@NonNull final Long twoId) {
        TwoPointGrading entity = twoPointGradingRepository.findById(twoId).orElseThrow(() -> new EntityNotFoundException(TWO_NOT_FOUND + twoId));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setDeleted(true);
    }

    //----------------------------------ONE-----------------------------------

    @Transactional(readOnly = true)
    public TwoPointGradingOutDto findOneForEdit(@NonNull final Long twoId) {
        return twoPointGradingDtoTransformer.toDto(twoPointGradingRepository.findOneForEdit(twoId));
    }

    //-----------------------------INSTRUCTOR search---------------------------

    @Transactional(readOnly = true)
    public Slice<TwoPointGradingOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return twoPointGradingRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(twoPointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<TwoPointGradingOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return twoPointGradingRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(twoPointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<TwoPointGradingOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return twoPointGradingRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(twoPointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<TwoPointGradingOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return twoPointGradingRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(twoPointGradingDtoTransformer::toDto);
    }
}
