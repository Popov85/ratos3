package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.grading.FourPointGrading;
import ua.edu.ratos.dao.repository.FourPointGradingRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.FourPointGradingInDto;
import ua.edu.ratos.service.dto.out.grading.FourPointGradingOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoFourPointGradingTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.FourPointGradingDtoTransformer;
import javax.persistence.EntityNotFoundException;

@Service
public class FourPointGradingService {

    private static final String ID_IS_NOT_INCLUDED = "FourId is not included, reject update!";

    private static final String FOUR_NOT_FOUND = "The requested FourPointGrading not found, fourId = ";

    private static final String DEFAULT_GRADINGS_CANNOT_BE_MODIFIED = "The default FourPointGrading cannot be modified";

    private FourPointGradingRepository fourPointGradingRepository;

    private FourPointGradingDtoTransformer fourPointGradingDtoTransformer;

    private DtoFourPointGradingTransformer dtoFourPointGradingTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setFourPointGradingRepository(FourPointGradingRepository fourPointGradingRepository) {
        this.fourPointGradingRepository = fourPointGradingRepository;
    }

    @Autowired
    public void setFourPointGradingDtoTransformer(FourPointGradingDtoTransformer fourPointGradingDtoTransformer) {
        this.fourPointGradingDtoTransformer = fourPointGradingDtoTransformer;
    }

    @Autowired
    public void setDtoFourPointGradingTransformer(DtoFourPointGradingTransformer dtoFourPointGradingTransformer) {
        this.dtoFourPointGradingTransformer = dtoFourPointGradingTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //-------------------------------------------------------CRUD-------------------------------------------------------

    @Transactional
    public Long save(@NonNull final FourPointGradingInDto dto) {
        return fourPointGradingRepository.save(dtoFourPointGradingTransformer.toEntity(dto)).getFourId();
    }

    @Transactional
    public void update(@NonNull final FourPointGradingInDto dto) {
        Long fourId = dto.getFourId();
        if (fourId == null || fourId ==0)
            throw new RuntimeException(ID_IS_NOT_INCLUDED);
        FourPointGrading entity = fourPointGradingRepository.findById(fourId)
                .orElseThrow(() -> new EntityNotFoundException(FOUR_NOT_FOUND + fourId));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setName(dto.getName());
        entity.setThreshold3(dto.getThreshold3());
        entity.setThreshold4(dto.getThreshold4());
        entity.setThreshold5(dto.getThreshold5());
    }

    @Transactional
    public void deleteById(@NonNull final Long fourId) {
        FourPointGrading entity = fourPointGradingRepository.findById(fourId).orElseThrow(() -> new EntityNotFoundException(FOUR_NOT_FOUND + fourId));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setDeleted(true);
    }

    //--------------------------------------------------One (for update)------------------------------------------------

    @Transactional(readOnly = true)
    public FourPointGradingOutDto findOneForEdit(@NonNull final Long fourId) {
        return fourPointGradingDtoTransformer.toDto(fourPointGradingRepository.findOneForEdit(fourId));
    }

    //----------------------------------------------------Staff table---------------------------------------------------

    @Transactional(readOnly = true)
    public Slice<FourPointGradingOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return fourPointGradingRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(fourPointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<FourPointGradingOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return fourPointGradingRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(fourPointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<FourPointGradingOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return fourPointGradingRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(fourPointGradingDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<FourPointGradingOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return fourPointGradingRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(fourPointGradingDtoTransformer::toDto);
    }
}
