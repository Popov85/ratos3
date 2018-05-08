package ua.edu.ratos.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class Theme {
    private long themeId;
    private String name;
    private List<Question> questions;
}
