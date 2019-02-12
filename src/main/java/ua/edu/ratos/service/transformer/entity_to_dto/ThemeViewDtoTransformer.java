package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.ThemeView;
import ua.edu.ratos.service.dto.out.view.ThemeViewOutDto;
import ua.edu.ratos.service.dto.out.view.TypeOutDto;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ThemeViewDtoTransformer {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Set<ThemeViewOutDto> toDto(@NonNull final Set<ThemeView> themeViews) {

        Set<ThemeViewOutDto> result = new TreeSet<>(Comparator.comparing(ThemeViewOutDto::getTheme));

        // Group all entities in this collection by theme
        Map<String, List<ThemeView>> viewsByTheme = themeViews
                .stream()
                .collect(Collectors.groupingBy(ThemeView::getTheme));

        for (List<ThemeView> views : viewsByTheme.values()) {
            // Note, each theme has AT LEAST one ThemeView,
            // So let's take the first one to create ThemeView DTO
            ThemeView entity = views.get(0);
            Set<TypeOutDto> typeDetails = new HashSet<>();

            // Count total by theme and create Type DTO
            int totalQuestions = 0;
            for (ThemeView view : views) {
                TypeOutDto typeDto = modelMapper.map(view, TypeOutDto.class)
                        .setTypeId(view.getThemeViewId().getTypeId());
                typeDetails.add(typeDto);
                totalQuestions+=view.getQuestions();
            }
            result.add(new ThemeViewOutDto()
                    .setThemeId(entity.getThemeViewId().getThemeId())
                    .setTheme(entity.getTheme())
                    .setTotalQuestions(totalQuestions)
                    .setTypeDetails(typeDetails));
        }
        return result;
    }
}
