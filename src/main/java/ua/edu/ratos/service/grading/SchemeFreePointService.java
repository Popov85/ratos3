package ua.edu.ratos.service.grading;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;
import ua.edu.ratos.dao.entity.grading.SchemeFreePoint;
import ua.edu.ratos.dao.repository.SchemeFreePointRepository;
import ua.edu.ratos.service.dto.out.grading.FreePointGradingOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.FreePointGradingDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@AllArgsConstructor
public class SchemeFreePointService implements SchemeGraderService {

    @PersistenceContext
    private final EntityManager em;

    private final SchemeFreePointRepository repository;

    private final FreePointGradingDtoTransformer transformer;


    @Override
    public FreePointGradingOutDto save(long schemeId, long gradingDetailsId) {
        SchemeFreePoint schemeFreePoint = new SchemeFreePoint();
        schemeFreePoint.setSchemeId(schemeId);
        schemeFreePoint.setFreePointGrading(em.getReference(FreePointGrading.class, gradingDetailsId));
        schemeFreePoint = repository.save(schemeFreePoint);
        return transformer.toDto(schemeFreePoint.getFreePointGrading());
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
    public Long name() {
        return 3L;
    }
}
