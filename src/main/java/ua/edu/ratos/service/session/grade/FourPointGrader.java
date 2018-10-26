package ua.edu.ratos.service.session.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.domain.entity.grade.SchemeFourPoint;
import ua.edu.ratos.domain.repository.SchemeFourPointRepository;
import java.util.Optional;

@Component
public class FourPointGrader implements Grader {

    @Autowired
    private SchemeFourPointRepository schemeFourPointRepository;

    @Override
    public GradedResult grade(double percent, long schemeId) {
        final Optional<SchemeFourPoint> schemeFourPoint = schemeFourPointRepository.findById(schemeId);
        return schemeFourPoint.get().getFourPointGrading().grade(percent);
    }

    @Override
    public String type() {
        return "four-point";
    }
}
