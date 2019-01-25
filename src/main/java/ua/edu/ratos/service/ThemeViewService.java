package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.ThemeViewRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.out.view.ThemeViewOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.ThemeViewDtoTransformer;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ThemeViewService {

    private ThemeViewRepository themeViewRepository;

    private ThemeViewDtoTransformer themeViewDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setThemeViewRepository(ThemeViewRepository themeViewRepository) {
        this.themeViewRepository = themeViewRepository;
    }

    @Autowired
    public void setThemeViewDtoTransformer(ThemeViewDtoTransformer themeViewDtoTransformer) {
        this.themeViewDtoTransformer = themeViewDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //-------------------------------Statistics on theme---------------------------

    public ThemeViewOutDto findOneByThemeId(@NonNull final Long themeId) {
        return themeViewDtoTransformer.toDto(themeViewRepository.findAllByThemeId(themeId)).iterator().next();
    }


    //-----------------------------------Instructor---------------------------------
    // Page is not applicable here, as it doesn't make sense,
    // each set is reduced in size programmatically, hence no correct info about page size, etc.

    public Set<ThemeViewOutDto> findAllByDepartmentId() {
        return themeViewDtoTransformer.toDto(themeViewRepository.findAllByDepartmentId(securityUtils.getAuthDepId()));
    }

    public Set<ThemeViewOutDto> findAllByDepartmentIdAndThemeLettersContains(@NonNull final String letters) {
        return themeViewDtoTransformer.toDto(themeViewRepository.findAllByDepartmentIdAndThemeLettersContains(securityUtils.getAuthDepId(), letters));
    }

    public Set<ThemeViewOutDto> findAllByDepartmentIdAndCourseId(@NonNull final Long courseId) {
        return themeViewDtoTransformer.toDto(themeViewRepository.findAllByDepartmentIdAndCourseId(securityUtils.getAuthDepId(), courseId));
    }

    public Set<ThemeViewOutDto> findAllByDepartmentIdAndCourseIdAndThemeLettersContains(@NonNull final Long courseId, @NonNull final String letters) {
        return themeViewDtoTransformer.toDto(themeViewRepository.findAllByDepartmentIdAndCourseIdAndThemeLettersContains(securityUtils.getAuthDepId(), courseId, letters));
    }
}
