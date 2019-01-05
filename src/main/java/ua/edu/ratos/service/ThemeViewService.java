package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.ThemeView;
import ua.edu.ratos.dao.repository.ThemeViewRepository;
import ua.edu.ratos.service.transformer.domain_to_dto.ThemeViewDtoTransformer;
import ua.edu.ratos.service.dto.out.view.ThemeOutDto;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ThemeViewService {

    @Autowired
    private ThemeViewRepository themeViewRepository;

    @Autowired
    private ThemeViewDtoTransformer transformer;

    public Set<ThemeOutDto> findAllByCourseId(@NonNull Long courseId) {
        final Set<ThemeView> themes = themeViewRepository.findAllByCourseId(courseId);
        return transformer.toDto(themes);
    }

    public Set<ThemeOutDto> findAllByCourseIdAndThemeLettersContains(@NonNull Long courseId, @NonNull String contains) {
        final Set<ThemeView> themes = themeViewRepository.findAllByCourseIdAndThemeLettersContains(courseId, contains);
        return transformer.toDto(themes);
    }

    public Set<ThemeOutDto> findAllByDepartmentId(@NonNull Long depId, @NonNull Pageable pageable) {
        final Page<ThemeView> page = themeViewRepository.findAllByDepartmentId(depId, pageable);
        return transformer.toDto(page.getContent());
    }

    public Set<ThemeOutDto> findAllByDepartmentIdAndThemeLettersContains(@NonNull Long depId, @NonNull String contains) {
        final Set<ThemeView> themes = themeViewRepository.findAllByDepartmentIdAndThemeLettersContains(depId, contains);
        return transformer.toDto(themes);
    }

    public Set<ThemeOutDto> findAllByOrganisationId(@NonNull Long orgId, @NonNull Pageable pageable) {
        final Page<ThemeView> page = themeViewRepository.findAllByOrganisationId(orgId, pageable);
        return transformer.toDto(page.getContent());
    }

    public Set<ThemeOutDto> findAllByOrganisationIdAndThemeLettersContains(@NonNull Long orgId, @NonNull String contains) {
        final Set<ThemeView> themes = themeViewRepository.findAllByOrganisationIdAndThemeLettersContains(orgId, contains);
        return transformer.toDto(themes);
    }

}
