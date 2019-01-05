package ua.edu.ratos.service.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grade.SchemeTwoPoint;
import ua.edu.ratos.dao.entity.grade.TwoPointGrading;
import ua.edu.ratos.dao.repository.SchemeTwoPointRepository;
import javax.persistence.EntityManager;

@Service
public class SchemeTwoPointService implements SchemeGraderService {

    @Autowired
    private SchemeTwoPointRepository repository;

    @Autowired
    private EntityManager em;

    public void save(long schemeId, long gradingDetailsId) {
        SchemeTwoPoint SchemeTwoPoint = new SchemeTwoPoint();
        SchemeTwoPoint.setSchemeId(schemeId);
        SchemeTwoPoint.setTwoPointGrading(em.getReference(TwoPointGrading.class, gradingDetailsId));
        repository.save(SchemeTwoPoint);
    }

    @Override
    public void delete(long schemeId) {
        repository.deleteById(schemeId);
    }

    @Override
    public long type() {
        return 2;
    }
}
