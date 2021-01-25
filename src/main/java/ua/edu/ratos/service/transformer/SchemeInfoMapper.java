package ua.edu.ratos.service.transformer;

import org.mapstruct.*;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.service.dto.out.SchemeInfoOutDto;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ModeMinMapper.class})
public abstract class SchemeInfoMapper {

    @Mapping(target = "course", source = "entity.course.name")
    @Mapping(target = "strategy", source = "entity.strategy.name")
    @Mapping(target = "timings", source = "entity.settings.secondsPerQuestion")
    @Mapping(target = "batchTimeLimited", source = "entity.settings.strictControlTimePerQuestion")
    @Mapping(target = "staff", expression = "java(" +
            "entity.getStaff().getPosition().getName()" +
            ".concat(\" \")" +
            ".concat(String.valueOf(entity.getStaff().getUser().getName().charAt(0)))" +
            ".concat(\".\")" +
            ".concat(entity.getStaff().getUser().getSurname()))")
    public abstract SchemeInfoOutDto toDto(Scheme entity);

    @AfterMapping
    void decorate(Scheme entity, @MappingTarget SchemeInfoOutDto dto) {
        dto.setQuestions(getQuestionsCount(entity.getThemes()));
    }

    int getQuestionsCount(List<SchemeTheme> themes) {
        return themes.stream()
                .flatMapToInt(t -> t.getSettings().stream()
                        .mapToInt(s -> s.getLevel1()+s.getLevel2()+s.getLevel3()))
                .sum();
    }
}
