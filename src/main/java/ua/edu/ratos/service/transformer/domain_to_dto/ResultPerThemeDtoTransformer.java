package ua.edu.ratos.service.transformer.domain_to_dto;

import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.ResultPerTheme;
import ua.edu.ratos.service.dto.out.ThemeOutDto;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;

@Service
public class ResultPerThemeDtoTransformer {

    public ResultPerThemeOutDto toDto(ResultPerTheme r) {
        ThemeOutDto themeOutDto = new ThemeOutDto()
                .setThemeId(r.getTheme().getThemeId())
                .setName(r.getTheme().getName());
        return new ResultPerThemeOutDto()
                .setTheme(themeOutDto)
                .setPercent(r.getPercent())
                .setQuantity(r.getQuantity());
    }
}
