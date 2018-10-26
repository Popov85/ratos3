package ua.edu.ratos.service.scheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.grade.FourPointGrading;
import ua.edu.ratos.domain.entity.grade.SchemeFourPoint;
import ua.edu.ratos.domain.repository.SchemeFourPointRepository;

import javax.persistence.EntityManager;

@Service
public class SchemeFourPointService implements SchemeGraderService {

    @Autowired
    private SchemeFourPointRepository repository;

    @Autowired
    private EntityManager em;

    public void save(long schemeId, long gradingDetailsId) {
        SchemeFourPoint schemeFourPoint = new SchemeFourPoint();
        schemeFourPoint.setSchemeId(schemeId);
        schemeFourPoint.setFourPointGrading(em.getReference(FourPointGrading.class, gradingDetailsId));
        repository.save(schemeFourPoint);
    }

    @Override
    public void delete(long schemeId) {
        repository.deleteById(schemeId);
    }

    @Override
    public long type() {
        return 1;
    }

}
