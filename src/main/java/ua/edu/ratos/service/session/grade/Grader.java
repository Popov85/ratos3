package ua.edu.ratos.service.session.grade;

import ua.edu.ratos.service.NamedService;

public interface Grader extends NamedService<String> {

    GradedResult grade(double percent, long schemeId);

}
