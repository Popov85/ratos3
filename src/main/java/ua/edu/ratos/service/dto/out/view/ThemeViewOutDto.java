package ua.edu.ratos.service.dto.out.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ThemeViewOutDto {
    private long themeId;
    private String theme;
    private Set<TypeOutDto> typeDetails = new HashSet<>();
    private int totalQuestions;
}
