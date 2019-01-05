package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.dao.repository.ModeRepository;
import ua.edu.ratos.service.dto.in.ModeInDto;
import ua.edu.ratos.service.dto.out.ModeOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoModeTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ModeDtoTransformer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ModeService {

    @Autowired
    private ModeRepository modeRepository;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private DtoModeTransformer dtoModeTransformer;

    @Autowired
    private ModeDtoTransformer modeDtoTransformer;

    @Transactional
    public Long save(@NonNull ModeInDto dto) {
        return modeRepository.save(dtoModeTransformer.toEntity(dto)).getModeId();
    }

    @Transactional
    public void update(@NonNull Long modeId, @NonNull ModeInDto dto) {
        if (!modeRepository.existsById(modeId))
            throw new RuntimeException("Failed to update the Mode: ID does not exist");
        modeRepository.save(dtoModeTransformer.toEntity(modeId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long modeId) {
        modeRepository.findById(modeId).get().setDeleted(true);
    }

    /*-------------------SELECT----------------------*/

    @Transactional(readOnly = true)
    public Page<ModeOutDto> findAll(@NonNull Pageable pageable) {
        Page<Mode> page = modeRepository.findAll(pageable);
        return new PageImpl<>(toDto(page.getContent()), pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<ModeOutDto> findAllByStaffId(@NonNull Long staffId) {
        final Set<Mode> allDefault = modeRepository.findAllDefault();
        final Set<Mode> allByStaffId = modeRepository.findAllByStaffId(staffId);
        List<Mode> result = new ArrayList<>(allDefault);
        result.addAll(allByStaffId);
        return toDto(result);
    }

    @Transactional(readOnly = true)
    public List<ModeOutDto> findAllByStaffIdAndModeNameLettersContains(@NonNull Long staffId, @NonNull String contains) {
        Set<Mode> content = modeRepository.findAllByStaffIdAndModeNameLettersContains(staffId, contains);
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<ModeOutDto> findAllByDepartmentId(@NonNull Long depId) {
        final Set<Mode> allDefault = modeRepository.findAllDefault();
        final List<Mode> allByDepId = modeRepository.findByDepartmentId(depId, propertiesService.getInitCollectionSize()).getContent();
        List<Mode> result = new ArrayList<>(allDefault);
        result.addAll(allByDepId);
        return toDto(result);
    }

    @Transactional(readOnly = true)
    public List<ModeOutDto> findAllByDepartmentIdAndModeNameLettersContains(@NonNull Long depId, @NonNull String contains) {
        Set<Mode> content = modeRepository.findAllByDepartmentIdAndModeNameLettersContains(depId, contains);
        return toDto(content);
    }


    private List<ModeOutDto> toDto(@NonNull final Collection<Mode> content) {
        return content.stream().map(modeDtoTransformer::toDto).collect(Collectors.toList());
    }
}
