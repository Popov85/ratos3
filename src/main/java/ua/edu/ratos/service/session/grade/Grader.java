package ua.edu.ratos.service.session.grade;

public interface Grader {
    GradedResult grade(double percent, long schemeId);
    String type();
}
