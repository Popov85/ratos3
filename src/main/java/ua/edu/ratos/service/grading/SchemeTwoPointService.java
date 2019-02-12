package ua.edu.ratos.service.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grade.SchemeTwoPoint;
import ua.edu.ratos.dao.entity.grade.TwoPointGrading;
import ua.edu.ratos.dao.repository.SchemeTwoPointRepository;
import ua.edu.ratos.service.dto.out.grading.TwoPointGradingOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.TwoPointGradingDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class SchemeTwoPointService implements SchemeGraderService {

    @PersistenceContext
    private EntityManager em;

    private SchemeTwoPointRepository repository;

    private TwoPointGradingDtoTransformer transformer;

    @Autowired
    public void setRepository(SchemeTwoPointRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setTransformer(TwoPointGradingDtoTransformer transformer) {
        this.transformer = transformer;
    }



    @Override
    public void save(long schemeId, long gradingDetailsId) {
        SchemeTwoPoint SchemeTwoPoint = new SchemeTwoPoint();
        SchemeTwoPoint.setSchemeId(schemeId);
        SchemeTwoPoint.setTwoPointGrading(em.getReference(TwoPointGrading.class, gradingDetailsId));
        repository.save(SchemeTwoPoint);
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
    public long type() {
        return 2;
    }
}
