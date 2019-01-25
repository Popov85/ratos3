package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.HelpInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoHelpTransformer {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SecurityUtils securityUtils;

    @Transactional(propagation = Propagation.MANDATORY)
    public Help toEntity(@NonNull final HelpInDto dto) {
        Help help = new Help();
        help.setName(dto.getName());
        help.setHelp(dto.getHelp());
        help.setStaff(em.getReference(Staff.class, securityUtils.getAuthStaffId()));
        if (dto.getResourceId()!=0) {
            help.addResource(em.find(Resource.class, dto.getResourceId()));
        }
        return help;
    }
}
