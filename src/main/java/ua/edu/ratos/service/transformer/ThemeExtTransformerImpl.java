package ua.edu.ratos.service.transformer;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.repository.QuestionRepository;
import ua.edu.ratos.dao.repository.projections.TypeAndCount;
import ua.edu.ratos.service.dto.out.ThemeExtOutDto;
import ua.edu.ratos.service.dto.out.TypeMinOutDto;
import ua.edu.ratos.service.transformer.mapper.AccessMapper;
import ua.edu.ratos.service.transformer.mapper.CourseMinLMSMapper;
import ua.edu.ratos.service.transformer.mapper.StaffMinMapper;
import ua.edu.ratos.service.transformer.ThemeExtTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ThemeExtTransformerImpl implements ThemeExtTransformer {

    private final QuestionRepository questionRepository;

    private final CourseMinLMSMapper courseMinLMSMapper;

    private final StaffMinMapper staffMinMapper;

    private final AccessMapper accessMapper;

    @Override
    public ThemeExtOutDto toDto(@NonNull final Theme entity) {
        Set<TypeAndCount> typesAndCount = questionRepository.countAllTypesByThemeId(entity.getThemeId());
        return ((ThemeExtOutDto) new ThemeExtOutDto()
                .setThemeId(entity.getThemeId())
                .setName(entity.getName())
                .setCreated(entity.getCreated())
                .setCourse(courseMinLMSMapper.toDto(entity.getCourse()))
                .setStaff(staffMinMapper.toDto(entity.getStaff()))
                .setAccess(accessMapper.toDto(entity.getAccess())))
                .setTotal(typesAndCount.stream().mapToInt(TypeAndCount::getCount).sum())
                .setTotalByType(toDto(typesAndCount));
    }

    private Set<TypeMinOutDto> toDto(Set<TypeAndCount> typesAndCount) {
        return typesAndCount.stream().map(t->
                new TypeMinOutDto().setTypeId(t.getType()).setType(t.getAbbreviation()).setQuestions(t.getCount()))
                .collect(Collectors.toSet());
    }
}
