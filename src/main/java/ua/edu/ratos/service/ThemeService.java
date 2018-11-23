package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.repository.ThemeRepository;
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

    @Transactional
    public void update(@NonNull Long themeId, @NonNull ThemeInDto dto) {
        themeRepository.save(transformer.fromDto(themeId, dto));
    }

    @Transactional
    public void deleteById(Long themeId) {
        themeRepository.findById(themeId).get().setDeleted(true);
    }

    /*-------------------------SELECT---------------------*/

    @Transactional(readOnly = true)
    public Set<Theme> findAllByCourseId(@NonNull Long courseId) {
        return themeRepository.findAllByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<Theme> findAllByDepartmentId(@NonNull Long depId) {
        return themeRepository.findByDepartmentId(depId, PageRequest.of(0, 50)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Theme> findAllByFacultyId(@NonNull Long facId) {
        return themeRepository.findByFacultyId(facId, PageRequest.of(0, 50)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Theme> findAllByOrganisationId(@NonNull Long depId) {
        return themeRepository.findByOrganisationId(depId, propertiesService.getInitCollectionSize()).getContent();
    }

}
