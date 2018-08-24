package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.repository.ThemeRepository;
import ua.edu.ratos.service.dto.entity.ThemeInDto;
import ua.edu.ratos.service.dto.transformer.DtoThemeTransformer;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ThemeService {

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    private DtoThemeTransformer transformer;

    @Autowired
    private PropertiesService propertiesService;

    @Transactional
    public Long save(@NonNull ThemeInDto dto) {
        Theme theme = transformer.fromDto(dto);
        return themeRepository.save(theme).getThemeId();
    }

    @Transactional(readOnly = true)
    public Set<Theme> findAllByCourseId(@NonNull Long courseId) {
        return themeRepository.findAllByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public Set<Theme> findAllByDepartmentId(@NonNull Long depId) {
        return themeRepository.findAllByDepartmentId(depId);
    }

    @Transactional(readOnly = true)
    public List<Theme> findAllByOrganisationId(@NonNull Long depId) {
        return themeRepository.findByOrganisationId(depId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional
    public void update(@NonNull ThemeInDto dto) {
        if (dto.getThemeId()==null || dto.getThemeId()==0)
            throw new RuntimeException("Invalid ID");
        Theme theme = transformer.fromDto(dto);
        themeRepository.save(theme);
    }

    @Transactional
    public void deleteById(Long themeId) {
        themeRepository.pseudoDeleteById(themeId);
    }

}
