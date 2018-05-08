package ua.edu.ratos.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class Question {
    private long questionId;
    private String question;
    private short level;
    private Theme theme;
    private TypeOfQuestion type;
    private List<Answer> answers;
    private Optional<HelpOfQuestion> help;
    private Optional<Resource> resource;
}
