package ua.edu.ratos.service.dto.transformer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.service.dto.entity.HelpInDto;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@Component
public class DtoHelpTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Help fromDto(HelpInDto dto) {
        Help help = modelMapper.map(dto, Help.class);
        help.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        if (dto.getResourceId()!=0) {
            help.addResource(em.find(Resource.class, dto.getResourceId()));
        } else {
            help.getResources().clear();
        }
        return help;
    }
}
