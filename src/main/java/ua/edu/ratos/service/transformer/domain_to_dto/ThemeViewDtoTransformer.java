package ua.edu.ratos.service.transformer.domain_to_dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ThemeView;
import ua.edu.ratos.dao.entity.ThemeViewId;
import ua.edu.ratos.service.dto.out.view.ThemeOutDto;
import ua.edu.ratos.service.dto.out.view.TypeOutDto;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ThemeViewDtoTransformer {

    @Autowired
    private ModelMapper modelMapper;

    public Set<ThemeOutDto> toDto(Set<ThemeView> themeViews) {
        Set<ThemeOutDto> result = new HashSet<>();
        Map<String, List<ThemeView>> questionsByTheme = themeViews.stream().collect(
                Collectors.groupingBy(ThemeView::getTheme));
        for (List<ThemeView> views : questionsByTheme.values()) {
            final ThemeView themeView = views.get(0);
            final ThemeViewId themeViewId = themeView.getThemeViewId();

            Long themeId = themeViewId.getThemeId();
            final String theme = themeView.getTheme();

            Long orgId = themeViewId.getOrgId();
            final String organisation = themeView.getOrganisation();

            Long facId = themeViewId.getFacId();
            final String faculty = themeView.getFaculty();

            Long depId = themeViewId.getDepId();
            final String department = themeView.getDepartment();

            Long courseId = themeViewId.getCourseId();
            final String course = themeView.getCourse();

            ThemeOutDto out = new ThemeOutDto()
                    .setThemeId(themeId)
                    .setTheme(theme)
                    .setOrgId(orgId)
                    .setOrganisation(organisation)
                    .setFacId(facId)
                    .setFaculty(faculty)
                    .setDepId(depId)
                    .setDepartment(department)
                    .setCourseId(courseId)
                    .setCourse(course);

            int totalQuestions = 0;
            for (ThemeView view : views) {
                TypeOutDto typeDto = modelMapper.map(view, TypeOutDto.class)
                        .setTypeId(view.getThemeViewId().getTypeId());
                out.addTypeDetails(typeDto);
                totalQuestions+=view.getQuestions();
            }
            out.setTotalQuestions(totalQuestions);
            result.add(out);
        }
        return result;
    }


    public Set<ThemeOutDto> toDto(List<ThemeView> themeViews) {
        Set<ThemeView> set = new HashSet<>(themeViews);
        return toDto(set);
    }

}
