package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.GradingDomain;
import ua.edu.ratos.service.domain.SchemeDomain;
import ua.edu.ratos.service.session.grade.GradedResult;
import ua.edu.ratos.service.session.grade.Grader;
import ua.edu.ratos.service.session.grade.GradingFactory;


@Slf4j
@Service
public class GradingService {

    @Autowired
    private GradingFactory gradingFactory;

    /**
     * Grader the overall result based on gradingDomain system specified by settingsDomain
     * @param percent
     * @param schemeDomain
     * @return overall outcome in the given scale
     */
    public GradedResult grade(final double percent, @NonNull final SchemeDomain schemeDomain) {
        final GradingDomain gradingDomain = schemeDomain.getGradingDomain();
        final Grader grader = gradingFactory.getGrader(gradingDomain.getName());
        return grader.grade(percent, schemeDomain.getSchemeId());
    }

}
