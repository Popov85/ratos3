package ua.edu.ratos.domain.model;

import lombok.Getter;
import lombok.Setter;
import ua.edu.ratos.domain.question.Question;

import java.util.List;

@Setter
@Getter
public class Theme {
    private long themeId;
    private String name;
    private List<Question> questions;
}
