package ua.edu.ratos.service.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grade.FreePointGrading;
import ua.edu.ratos.dao.entity.grade.SchemeFreePoint;
import ua.edu.ratos.dao.repository.SchemeFreePointRepository;
import ua.edu.ratos.service.dto.out.grading.FreePointGradingOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.FreePointGradingDtoTransformer;

import javax.persistence.EntityManager;

@Service
public class SchemeFreePointService implements SchemeGraderService {

    @Autowired
    private SchemeFreePointRepository repository;

    @Autowired
    private EntityManager em;

    @Autowired
    private FreePointGradingDtoTransformer transformer;

    @Override
    public void save(long schemeId, long gradingDetailsId) {
        SchemeFreePoint schemeFreePoint = new SchemeFreePoint();
        schemeFreePoint.setSchemeId(schemeId);
        schemeFreePoint.setFreePointGrading(em.getReference(FreePointGrading.class, gradingDetailsId));
        repository.save(schemeFreePoint);
    }

    @Override
    public FreePointGradingOutDto findDetails(long schemeId) {
        return transformer.toDto(repository.findForDtoById(schemeId).getFreePointGrading());
    }

    @Override
    public void delete(long schemeId) {
        repository.deleteById(schemeId);
    }

    @Override
    public long type() {
        return 3;
    }
}
