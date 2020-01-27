package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.dao.repository.projections.TypeAndCount;
import ua.edu.ratos.service.dto.out.ThemeExtOutDto;
import ua.edu.ratos.service.dto.out.TypeMinOutDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class ThemeExtDtoTransformer {

    private final QuestionRepository questionRepository;

    private final CourseMinLMSDtoTransformer courseMinLMSDtoTransformer;

    private final StaffMinDtoTransformer staffDtoTransformer;

    private final AccessDtoTransformer accessDtoTransformer;


    public ThemeExtOutDto toDto(@NonNull final Theme entity) {
        Set<TypeAndCount> typesAndCount = questionRepository.countAllTypesByThemeId(entity.getThemeId());
        return ((ThemeExtOutDto) new ThemeExtOutDto()
                .setThemeId(entity.getThemeId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setCourse(courseMinLMSDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffDtoTransformer.toDto(entity.getStaff()))
                .setAccess(accessDtoTransformer.toDto(entity.getAccess())))
                .setTotal(typesAndCount.stream().mapToInt(TypeAndCount::getCount).sum())
                .setTotalByType(toDto(typesAndCount));
    }

    private Set<TypeMinOutDto> toDto(Set<TypeAndCount> typesAndCount) {
        return typesAndCount.stream().map(t->
            new TypeMinOutDto().setTypeId(t.getType()).setType(t.getAbbreviation()).setQuestions(t.getCount()))
                .collect(Collectors.toSet());
    }

}
