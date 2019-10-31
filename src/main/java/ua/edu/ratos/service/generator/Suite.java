package ua.edu.ratos.service.generator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Profile;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuppressWarnings("SpellCheckingInspection")
@Profile({"dev", "demo"})
public class Suite {
    private int organisations;
    private int faculties;
    private int departments;
    private int classes;
    private int courses;
    private int themes;
    private int complexThemes;
    private int stepThemes;
    private int schemes; // single theme schemes
    private int complexSchemes; // multiple 3-5 themes schemes
    private int stepSchemes; // STEP-like scheme with 8-10 themes

    private int students;
    private int results;

    private int resources;
    private int helps;

    private int phrases;

    private int mcq;
    private int complexMcq;
    private int stepMcq;
    private int fbsq;
    private int fbmq;
    private int mq;
    private int sq;
}
