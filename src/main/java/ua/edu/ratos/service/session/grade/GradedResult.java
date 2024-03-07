package ua.edu.ratos.service.session.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GradedResult {
    private final boolean passed;
    private final double grade;
}
