package ua.edu.ratos.service.dto.transformer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.domain.entity.ThemeView;
import ua.edu.ratos.service.dto.view.ThemeOutDto;
import ua.edu.ratos.service.dto.view.TypeOutDto;
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
        for (Map.Entry<String, List<ThemeView>> entry : questionsByTheme.entrySet()) {
            final String theme = entry.getKey();
            final List<ThemeView> views = entry.getValue();
            Long themeId = views.get(0).getThemeViewId().getThemeId();
            ThemeOutDto out = new ThemeOutDto()
                    .setThemeId(themeId)
                    .setTheme(theme);
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
