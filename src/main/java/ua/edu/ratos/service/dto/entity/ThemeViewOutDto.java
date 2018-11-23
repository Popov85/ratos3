package ua.edu.ratos.service.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.dao.entity.ThemeView;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ThemeViewOutDto {
    private final List<ThemeView> themeViews;
    private final int totalQuantity;
}
