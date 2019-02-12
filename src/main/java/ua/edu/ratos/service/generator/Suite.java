package ua.edu.ratos.service.generator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Suite {
    private int organisations;
    private int faculties;
    private int departments;
    private int classes;
    private int courses;
    private int themes;
    private int schemes;
    private int students;
    private int results;
}
