package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.service.dto.in.FacultyInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Deprecated
@Component
@AllArgsConstructor
public class DtoFacultyTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Faculty toEntity(@NonNull final FacultyInDto dto) {
        Faculty faculty = modelMapper.map(dto, Faculty.class);
        faculty.setOrganisation(em.getReference(Organisation.class, dto.getOrgId()));
        return faculty;
    }
}
