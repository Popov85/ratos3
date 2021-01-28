package ua.edu.ratos.service.transformer.domain_to_dto;

import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.ResultPerTheme;
import ua.edu.ratos.service.dto.out.ThemeMinOutDto;
import ua.edu.ratos.service.dto.session.ResultPerThemeOutDto;

@Deprecated
@Service
public class ResultPerThemeDtoTransformer {

    public ResultPerThemeOutDto toDto(ResultPerTheme r) {
        ThemeMinOutDto themeOutDto = new ThemeMinOutDto()
                .setThemeId(r.getTheme().getThemeId())
                .setName(r.getTheme().getName());
        return new ResultPerThemeOutDto()
                .setTheme(themeOutDto)
                .setPercent(r.getPercent())
                .setQuantity(r.getQuantity());
    }
}
