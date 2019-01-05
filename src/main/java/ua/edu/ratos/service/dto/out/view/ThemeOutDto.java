package ua.edu.ratos.service.dto.out.view;

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

    private long orgId;
    private String organisation;

    private long facId;
    private String faculty;

    private long depId;
    private String department;

    private long courseId;
    private String course;

    private int totalQuestions;
    private Set<TypeOutDto> typeDetails = new HashSet<>();

    public void addTypeDetails(@NonNull TypeOutDto dto) {
        this.typeDetails.add(dto);
    }
}
