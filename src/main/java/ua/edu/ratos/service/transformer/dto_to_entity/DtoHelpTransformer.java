package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.in.HelpInDto;
import javax.persistence.EntityManager;

@Component
public class DtoHelpTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Help toEntity(@NonNull HelpInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Help toEntity(Long helpId, @NonNull HelpInDto dto) {
        Help help = modelMapper.map(dto, Help.class);
        help.setHelpId(helpId);
        help.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        if (dto.getResourceId()!=0) {
            help.addResource(em.find(Resource.class, dto.getResourceId()));
        }
        return help;
    }
}
