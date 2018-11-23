package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.entity.ResourceInDto;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
public class DtoResourceTransformer {
    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Resource fromDto(@NonNull ResourceInDto dto) {
        return fromDto(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Resource fromDto(Long resId, @NonNull ResourceInDto dto) {
        final Resource resource = modelMapper.map(dto, Resource.class);
        resource.setResourceId(resId);
        resource.setStaff((em.getReference(Staff.class, dto.getStaffId())));
        resource.setLastUsed(LocalDateTime.now());
        return resource;
    }
}
