package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.grading.FourPointGrading;
import ua.edu.ratos.dao.entity.grading.Grading;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.FourPointGradingInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoFourPointGradingTransformer {

    private static final Long GRADING_ID = 1L;

    @PersistenceContext
    private EntityManager em;

    private SecurityUtils securityUtils;

    private ModelMapper modelMapper;

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FourPointGrading toEntity(@NonNull final FourPointGradingInDto dto) {
        FourPointGrading entity = modelMapper.map(dto, FourPointGrading.class);
        entity.setGrading(em.getReference(Grading.class, GRADING_ID));
        entity.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        return entity;
    }

}
