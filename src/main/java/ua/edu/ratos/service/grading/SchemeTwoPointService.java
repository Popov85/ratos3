package ua.edu.ratos.service.grading;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grading.SchemeTwoPoint;
import ua.edu.ratos.dao.entity.grading.TwoPointGrading;
import ua.edu.ratos.dao.repository.SchemeTwoPointRepository;
import ua.edu.ratos.service.dto.out.grading.TwoPointGradingOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.TwoPointGradingDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@AllArgsConstructor
public class SchemeTwoPointService implements SchemeGraderService {

    @PersistenceContext
    private final EntityManager em;

    private final SchemeTwoPointRepository repository;

    private final TwoPointGradingDtoTransformer transformer;


    @Override
    public TwoPointGradingOutDto save(long schemeId, long gradingDetailsId) {
        SchemeTwoPoint schemeTwoPoint = new SchemeTwoPoint();
        schemeTwoPoint.setSchemeId(schemeId);
        schemeTwoPoint.setTwoPointGrading(em.getReference(TwoPointGrading.class, gradingDetailsId));
        schemeTwoPoint = repository.save(schemeTwoPoint);
        return transformer.toDto(schemeTwoPoint.getTwoPointGrading());
    }

    @Override
    public TwoPointGradingOutDto findDetails(long schemeId) {
        return transformer.toDto(repository.findForDtoById(schemeId).getTwoPointGrading());
    }

    @Override
    public void delete(long schemeId) {
        repository.deleteById(schemeId);
    }

    @Override
    public Long name() {
        return 2L;
    }
}
