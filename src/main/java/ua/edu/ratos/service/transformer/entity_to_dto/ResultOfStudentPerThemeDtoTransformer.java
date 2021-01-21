package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.ResultOfStudentTheme;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;
import ua.edu.ratos.service.transformer.ThemeMinMapper;

@Service
@AllArgsConstructor
public class ResultOfStudentPerThemeDtoTransformer {

    private final ThemeMinMapper themeMinMapper;

    public ResultPerThemeOutDto toDto(ResultOfStudentTheme r) {

        return new ResultPerThemeOutDto()
                .setTheme(themeMinMapper.toDto(r.getTheme()))
                .setPercent(r.getPercent())
                .setQuantity(r.getQuantity());
    }
}
