package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.entity.HelpResource;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.entity.HelpInDto;
import javax.persistence.EntityManager;

@Component
public class DtoHelpTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Help fromDto(@NonNull HelpInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Help fromDto(Long helpId, @NonNull HelpInDto dto) {
        Help help = modelMapper.map(dto, Help.class);
        help.setHelpId(helpId);
        help.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        if (dto.getResourceId()!=0) {
            HelpResource helpResource = new HelpResource();
            helpResource.setHelpId(helpId);
            helpResource.setResource(em.find(Resource.class, dto.getResourceId()));
            helpResource.setHelp(help);
            help.setHelpResource(helpResource);
        }
        return help;
    }
}
