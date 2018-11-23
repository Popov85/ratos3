package ua.edu.ratos.service.session.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Scheme {

    private Long schemeId;

    private String name;

    private Strategy strategy;

    private Settings settings;

    private Mode mode;

    private Grading grading;
}
