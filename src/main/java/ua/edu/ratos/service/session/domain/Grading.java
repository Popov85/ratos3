package ua.edu.ratos.service.session.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Grading {
    private Long gradingId;

    private String name;
}
