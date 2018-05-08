package ua.edu.ratos.service.parsers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.Question;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class ParsingResult {
    private String header;
    private List<Question> questions;
    private List<Issue> issues;
}
