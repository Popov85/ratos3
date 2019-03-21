package ua.edu.ratos.service.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.grading.FourPointGrading;
import ua.edu.ratos.dao.entity.grading.SchemeFourPoint;
import ua.edu.ratos.dao.repository.SchemeFourPointRepository;
import ua.edu.ratos.service.dto.out.grading.FourPointGradingOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.FourPointGradingDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class SchemeFourPointService implements SchemeGraderService {

    @PersistenceContext
    private EntityManager em;

    private SchemeFourPointRepository repository;

    private FourPointGradingDtoTransformer transformer;
    @Autowired
    public void setRepository(SchemeFourPointRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setTransformer(FourPointGradingDtoTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public void save(long schemeId, long gradingDetailsId) {
        SchemeFourPoint schemeFourPoint = new SchemeFourPoint();
        schemeFourPoint.setSchemeId(schemeId);
        schemeFourPoint.setFourPointGrading(em.getReference(FourPointGrading.class, gradingDetailsId));
        repository.save(schemeFourPoint);
    }

    @Override
    public FourPointGradingOutDto findDetails(long schemeId) {
        return transformer.toDto(repository.findForDtoById(schemeId).getFourPointGrading());
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
