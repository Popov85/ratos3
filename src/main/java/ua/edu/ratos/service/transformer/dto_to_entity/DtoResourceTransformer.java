package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.in.ResourceInDto;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
public class DtoResourceTransformer {
    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Resource toEntity(@NonNull ResourceInDto dto) {
        return toEntity(null, dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Resource toEntity(Long resId, @NonNull ResourceInDto dto) {
        final Resource resource = modelMapper.map(dto, Resource.class);
        resource.setResourceId(resId);
        resource.setStaff((em.getReference(Staff.class, dto.getStaffId())));
        resource.setLastUsed(LocalDateTime.now());
        return resource;
    }
}
