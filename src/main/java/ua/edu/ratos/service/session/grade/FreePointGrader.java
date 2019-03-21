package ua.edu.ratos.service.session.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.grading.SchemeFreePoint;
import ua.edu.ratos.dao.repository.SchemeFreePointRepository;
import java.util.Optional;

@Component
public class FreePointGrader implements Grader {

    private SchemeFreePointRepository schemeFreePointRepository;

    @Autowired
    public void setSchemeFreePointRepository(SchemeFreePointRepository schemeFreePointRepository) {
        this.schemeFreePointRepository = schemeFreePointRepository;
    }

    @Override
    public GradedResult grade(double percent, long schemeId) {
        final Optional<SchemeFreePoint> schemeFreePoint = schemeFreePointRepository.findById(schemeId);
        return schemeFreePoint.get().getFreePointGrading().grade(percent);
    }

    @Override
    public String type() {
        return "free-point";
    }
}
