package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.dto.out.ThemeExtendedOutDto;
import ua.edu.ratos.service.dto.out.TypeMinOutDto;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ThemeExtendedDtoTransformer {

    private CourseDtoTransformer courseDtoTransformer;

    private StaffMinDtoTransformer staffDtoTransformer;

    private AccessDtoTransformer accessDtoTransformer;

    @Autowired
    public void setCourseDtoTransformer(CourseDtoTransformer courseDtoTransformer) {
        this.courseDtoTransformer = courseDtoTransformer;
    }

    @Autowired
    public void setStaffDtoTransformer(StaffMinDtoTransformer staffDtoTransformer) {
        this.staffDtoTransformer = staffDtoTransformer;
    }

    @Autowired
    public void setAccessDtoTransformer(AccessDtoTransformer accessDtoTransformer) {
        this.accessDtoTransformer = accessDtoTransformer;
    }

    public ThemeExtendedOutDto toDto(@NonNull final Theme entity) {
        return ((ThemeExtendedOutDto) new ThemeExtendedOutDto()
                .setThemeId(entity.getThemeId())
                .setName(entity.getName())
                .setCourse(courseDtoTransformer.toDto(entity.getCourse()))
                .setStaff(staffDtoTransformer.toDto(entity.getStaff()))
                .setAccess(accessDtoTransformer.toDto(entity.getAccess())))
                .setTotal(entity.getQuestions().size())
                .setTypeDetails(toDto(entity.getQuestions()));
    }

    private Set<TypeMinOutDto> toDto(Set<Question> questions) {
        Map<QuestionType, List<Question>> map = questions
                .stream()
                .collect(Collectors.groupingBy(Question::getType));
        return map.entrySet().stream()
                .map(m-> new TypeMinOutDto()
                    .setTypeId(m.getKey().getTypeId())
                    .setType(m.getKey().getAbbreviation())
                    .setQuestions(m.getValue().size()))
                .collect(Collectors.toSet());
    }

}
