package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FourPointGradingService {

    private static final String ID_IS_NOT_INCLUDED = "FourId is not included, reject update!";
    private static final String FOUR_NOT_FOUND = "The requested FourPointGrading not found, fourId = ";
    private static final String DEFAULT_GRADINGS_CANNOT_BE_MODIFIED = "The default FourPointGrading cannot be modified";

    private final FourPointGradingRepository fourPointGradingRepository;

    private final FourPointGradingDtoTransformer fourPointGradingDtoTransformer;

    private final DtoFourPointGradingTransformer dtoFourPointGradingTransformer;

    private final SecurityUtils securityUtils;

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

    //--------------------------------------------------One (for edit)--------------------------------------------------
    @Transactional(readOnly = true)
    public FourPointGradingOutDto findOneForEdit(@NonNull final Long fourId) {
        return fourPointGradingDtoTransformer.toDto(fourPointGradingRepository.findOneForEdit(fourId)
                .orElseThrow(()->new EntityNotFoundException(FOUR_NOT_FOUND+fourId)));
    }

    //------------------------------------------------------Default-----------------------------------------------------
    @Transactional(readOnly = true)
    public Set<FourPointGradingOutDto> findAllDefault() {
        return fourPointGradingRepository.findAllDefault()
                .stream()
                .map(fourPointGradingDtoTransformer::toDto)
                .collect(Collectors.toSet());
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
