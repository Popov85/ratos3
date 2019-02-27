package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.Class;
import ua.edu.ratos.service.dto.in.StudentInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoStudentTransformer {

    @PersistenceContext
    private EntityManager em;

    private DtoUserTransformer dtoUserTransformer;

    @Autowired
    public void setDtoUserTransformer(DtoUserTransformer dtoUserTransformer) {
        this.dtoUserTransformer = dtoUserTransformer;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Student toEntity(@NonNull final StudentInDto dto) {
        Student stud = new Student();
        stud.setStudId(dto.getStudId());
        User user = dtoUserTransformer.toEntity(dto.getUser());
        stud.setUser(user);
        stud.setEntranceYear(dto.getEntranceYear());
        stud.setStudentClass(em.getReference(Class.class, dto.getClassId()));
        stud.setFaculty(em.getReference(Faculty.class, dto.getFacId()));
        stud.setOrganisation(em.getReference(Organisation.class, dto.getOrgId()));
        return stud;
    }
}
