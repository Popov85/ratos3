package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.ResultOfStudentTheme;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;

@Service
@AllArgsConstructor
public class ResultOfStudentPerThemeDtoTransformer {

    private final ThemeMinDtoTransformer themeMinDtoTransformer;

    public ResultPerThemeOutDto toDto(ResultOfStudentTheme r) {

        return new ResultPerThemeOutDto()
                .setTheme(themeMinDtoTransformer.toDto(r.getTheme()))
                .setPercent(r.getPercent())
                .setQuantity(r.getQuantity());
    }
}
