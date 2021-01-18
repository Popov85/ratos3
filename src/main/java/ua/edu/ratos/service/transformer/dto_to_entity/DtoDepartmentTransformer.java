package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Faculty;
import ua.edu.ratos.service.dto.in.DepartmentInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Deprecated
@Component
@AllArgsConstructor
public class DtoDepartmentTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Department toEntity(@NonNull final DepartmentInDto dto) {
        Department department = modelMapper.map(dto, Department.class);
        department.setFaculty(em.getReference(Faculty.class, dto.getFacId()));
        return department;
    }
}
