package ua.edu.ratos.service.dto.view;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ThemeOutDto {
    private long themeId;
    private String theme;
    private int totalQuestions;
    private Set<TypeOutDto> typeDetails = new HashSet<>();

    public void addTypeDetails(@NonNull TypeOutDto dto) {
        this.typeDetails.add(dto);
    }
}
