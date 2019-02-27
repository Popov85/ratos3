package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.GradingDomain;
import ua.edu.ratos.service.session.grade.GradedResult;
import ua.edu.ratos.service.session.grade.Grader;
import ua.edu.ratos.service.session.grade.GradingFactory;

@Slf4j
@Service
public class GradingService {

    private GradingFactory gradingFactory;

    @Autowired
    public void setGradingFactory(GradingFactory gradingFactory) {
        this.gradingFactory = gradingFactory;
    }

    /**
     * Grader the overall result based on grading system specified by settings
     * @param percent
     * @param schemeId
     * @param gradingDomain
     * @return overall outcome in the given scale
     */
    public GradedResult grade(@NonNull final Long schemeId, @NonNull final GradingDomain gradingDomain, final double percent) {
        final Grader grader = gradingFactory.getGrader(gradingDomain.getName());
        return grader.grade(percent, schemeId);
    }

}
