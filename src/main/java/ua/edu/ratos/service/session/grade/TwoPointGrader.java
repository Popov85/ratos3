package ua.edu.ratos.service.session.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grade.SchemeTwoPoint;
import ua.edu.ratos.dao.repository.SchemeTwoPointRepository;
import java.util.Optional;

@Component
public class TwoPointGrader implements Grader {

    @Autowired
    private SchemeTwoPointRepository schemeTwoPointRepository;

    @Override
    public GradedResult grade(double percent, long schemeId) {
        final Optional<SchemeTwoPoint> schemeTwoPoint = schemeTwoPointRepository.findById(schemeId);
        return schemeTwoPoint.get().getTwoPointGrading().grade(percent);
    }

    @Override
    public String type() {
        return "two-point";
    }
}
