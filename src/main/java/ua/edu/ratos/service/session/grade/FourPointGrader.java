package ua.edu.ratos.service.session.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grading.SchemeFourPoint;
import ua.edu.ratos.dao.repository.SchemeFourPointRepository;
import java.util.Optional;

@Component
public class FourPointGrader implements Grader {

    private SchemeFourPointRepository schemeFourPointRepository;

    @Autowired
    public void setSchemeFourPointRepository(SchemeFourPointRepository schemeFourPointRepository) {
        this.schemeFourPointRepository = schemeFourPointRepository;
    }

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
