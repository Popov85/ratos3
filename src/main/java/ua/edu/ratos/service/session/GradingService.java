package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.Grading;
import ua.edu.ratos.service.session.domain.Scheme;
import ua.edu.ratos.service.session.grade.GradedResult;
import ua.edu.ratos.service.session.grade.Grader;
import ua.edu.ratos.service.session.grade.GradingFactory;


@Slf4j
@Service
public class GradingService {

    @Autowired
    private GradingFactory gradingFactory;

    /**
     * Grader the overall result based on grading system specified by settings
     * @param percent
     * @param scheme
     * @return overall outcome in the given scale
     */
    public GradedResult grade(final double percent, @NonNull final Scheme scheme) {
        final Grading grading = scheme.getGrading();
        final Grader grader = gradingFactory.getGrader(grading.getName());
        return grader.grade(percent, scheme.getSchemeId());
    }

}
