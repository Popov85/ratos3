package ua.edu.ratos.service.transformer.impl;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.dto.out.LevelsOutDto;
import ua.edu.ratos.service.dto.out.ThemeMapOutDto;
import ua.edu.ratos.service.transformer.ThemeMapTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ThemeMapTransformerImpl implements ThemeMapTransformer {

    public ThemeMapOutDto toDto(@NonNull final Long themeId, @NonNull final Set<Question> entities) {

        Map<Long, LevelsOutDto> typeLevelMap = new HashMap<>();

        // work with types map
        Map<QuestionType, List<Question>> map = entities.stream()
                .collect(Collectors.groupingBy(Question::getType));
        int totalByTheme = 0;
        for (Map.Entry<QuestionType, List<Question>> entry : map.entrySet()) {

            LevelsOutDto value = new LevelsOutDto()
                    .setType(entry.getKey().getAbbreviation());

            List<Question> typedQuestions = entry.getValue();

            // work with levels map
            Map<Byte, List<Question>> leveledQuestions = typedQuestions.stream()
                    .collect(Collectors.groupingBy(Question::getLevel));

            int level1Total = 0;
            int level2Total = 0;
            int level3Total = 0;

            if (leveledQuestions.get((byte) 1) != null) {
                level1Total = leveledQuestions.get((byte) 1).size();
                value.setTotalLevel1(level1Total);
            }
            if (leveledQuestions.get((byte) 2) != null) {
                level2Total = leveledQuestions.get((byte) 2).size();
                value.setTotalLevel2(level2Total);
            }
            if (leveledQuestions.get((byte) 3) != null) {
                level3Total = leveledQuestions.get((byte) 3).size();
                value.setTotalLevel3(level3Total);
            }
            int total = level1Total + level2Total + level3Total;

            value.setTotal(total);

            totalByTheme += total;
            typeLevelMap.put(entry.getKey().getTypeId(), value);
        }

        return new ThemeMapOutDto()
                .setThemeId(themeId)
                .setTotalByTheme(totalByTheme)
                .setTypeLevelMap(typeLevelMap);
    }
}
