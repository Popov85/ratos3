package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Mode;
import ua.edu.ratos.dao.repository.ModeRepository;
import ua.edu.ratos.service.dto.entity.ModeInDto;
import ua.edu.ratos.service.dto.transformer.DtoModeTransformer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ModeService {

    @Autowired
    private ModeRepository modeRepository;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private DtoModeTransformer transformer;

    @Transactional
    public Long save(@NonNull ModeInDto dto) {
        return modeRepository.save(transformer.fromDto(dto)).getModeId();
    }

    @Transactional
    public void update(@NonNull Long modeId, @NonNull ModeInDto dto) {
        if (!modeRepository.existsById(modeId))
            throw new RuntimeException("Failed to update mode: ID does not exist");
        modeRepository.save(transformer.fromDto(modeId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long modeId) {
        modeRepository.findById(modeId).get().setDeleted(true);
    }

    /*-------------------SELECT----------------------*/

    @Transactional(readOnly = true)
    public List<Mode> findAll( @NonNull Pageable pageable) {
        return modeRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<Mode> findAllByStaffId(@NonNull Long staffId) {
        final Set<Mode> allDefault = modeRepository.findAllDefault();
        final Set<Mode> allByStaffId = modeRepository.findAllByStaffId(staffId);
        List<Mode> result = new ArrayList<>(allDefault);
        result.addAll(allByStaffId);
        return result;
    }

    @Transactional(readOnly = true)
    public Set<Mode> findAllByStaffIdAndModeNameLettersContains(@NonNull Long staffId, @NonNull String contains) {
        return modeRepository.findAllByStaffIdAndModeNameLettersContains(staffId, contains);
    }


    @Transactional(readOnly = true)
    public List<Mode> findAllByDepartmentId(@NonNull Long depId) {
        final Set<Mode> allDefault = modeRepository.findAllDefault();
        final List<Mode> allByDepId = modeRepository.findByDepartmentId(depId, propertiesService.getInitCollectionSize()).getContent();
        List<Mode> result = new ArrayList<>(allDefault);
        result.addAll(allByDepId);
        return result;
    }

    @Transactional(readOnly = true)
    public Set<Mode> findAllByDepartmentIdAndModeNameLettersContains(@NonNull Long depId, @NonNull String contains) {
        return modeRepository.findAllByDepartmentIdAndModeNameLettersContains(depId, contains);
    }

}
