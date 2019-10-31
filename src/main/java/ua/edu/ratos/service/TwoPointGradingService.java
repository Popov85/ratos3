package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TwoPointGradingService {

    private static final String ID_IS_NOT_INCLUDED = "TwoId is not included, reject update!";
    private static final String TWO_NOT_FOUND = "The requested TwoPointGrading is not found, twoId = ";
    private static final String DEFAULT_GRADINGS_CANNOT_BE_MODIFIED = "The default TwoPointGrading cannot be modified";

    private final TwoPointGradingRepository twoPointGradingRepository;

    private final TwoPointGradingDtoTransformer twoPointGradingDtoTransformer;

    private final DtoTwoPointGradingTransformer dtoTwoPointGradingTransformer;

    private final SecurityUtils securityUtils;

    //-------------------------------------------------CRUD-------------------------------------------------------------
    @Transactional
    public Long save(@NonNull final TwoPointGradingInDto dto) {
        return twoPointGradingRepository.save(dtoTwoPointGradingTransformer.toEntity(dto)).getTwoId();
    }

    @Transactional
    public void update(@NonNull final TwoPointGradingInDto dto) {
        Long twoId = dto.getTwoId();
        if (twoId == null || twoId == 0) throw new RuntimeException(ID_IS_NOT_INCLUDED);
        TwoPointGrading entity = twoPointGradingRepository.findById(twoId)
                .orElseThrow(() -> new EntityNotFoundException(TWO_NOT_FOUND + twoId));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setName(dto.getName());
        entity.setThreshold(dto.getThreshold());
    }

    @Transactional
    public void deleteById(@NonNull final Long twoId) {
        TwoPointGrading entity = twoPointGradingRepository.findById(twoId)
                .orElseThrow(() -> new EntityNotFoundException(TWO_NOT_FOUND + twoId));
        if (entity.isDefault()) throw new RuntimeException(DEFAULT_GRADINGS_CANNOT_BE_MODIFIED);
        entity.setDeleted(true);
    }

    //-------------------------------------------------ONE--------------------------------------------------------------
    @Transactional(readOnly = true)
    public TwoPointGradingOutDto findOneForEdit(@NonNull final Long twoId) {
        return twoPointGradingDtoTransformer.toDto(twoPointGradingRepository.findOneForEdit(twoId)
                .orElseThrow(() -> new EntityNotFoundException(TWO_NOT_FOUND + twoId)));
    }

    //------------------------------------------------------Default-----------------------------------------------------
    @Transactional(readOnly = true)
    public Set<TwoPointGradingOutDto> findAllDefault() {
        return twoPointGradingRepository.findAllDefault()
                .stream()
                .map(twoPointGradingDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //-------------------------------------------------INSTRUCTOR search------------------------------------------------
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
