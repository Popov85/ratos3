package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.grading.TwoPointGrading;
import ua.edu.ratos.dao.entity.grading.Grading;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.TwoPointGradingInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoTwoPointGradingTransformer {

    private static final Long GRADING_ID = 2L;

    @PersistenceContext
    private EntityManager em;

    private ModelMapper modelMapper;

    private SecurityUtils securityUtils;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    public TwoPointGrading toEntity(@NonNull final TwoPointGradingInDto dto) {
        TwoPointGrading entity = modelMapper.map(dto, TwoPointGrading.class);
        entity.setGrading(em.getReference(Grading.class, GRADING_ID));
        entity.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        return entity;
    }
}
