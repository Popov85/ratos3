package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.dto.out.ThemeMapOutDto;

import java.util.Set;

public interface ThemeMapTransformer {

    ThemeMapOutDto toDto(Long themeId, Set<Question> entities);
}
