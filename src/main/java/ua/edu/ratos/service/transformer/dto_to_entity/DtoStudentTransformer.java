package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.repository.RoleRepository;
import ua.edu.ratos.service.dto.in.StudentInDto;
import ua.edu.ratos.service.transformer.UserMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Deprecated
@Component
@AllArgsConstructor
public class DtoStudentTransformer {

    private final static String ROLE = "ROLE_STUDENT";

    @PersistenceContext
    private final EntityManager em;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;


    @Transactional(propagation = Propagation.MANDATORY)
    public Student toEntity(@NonNull final StudentInDto dto) {
        Student stud = new Student();
        stud.setStudId(dto.getStudId());
        User user = userMapper.toEntity(dto.getUser());
        Optional<Role> role = roleRepository.findByName(ROLE);
        user.setRoles(new HashSet<>(Arrays.asList(role.orElseThrow(()->
                new EntityNotFoundException("ROLE_STUDENT is not found!")))));
        stud.setUser(user);
        stud.setEntranceYear(dto.getEntranceYear());
        stud.setStudentClass(em.getReference(Clazz.class, dto.getClassId()));
        stud.setFaculty(em.getReference(Faculty.class, dto.getFacId()));
        stud.setOrganisation(em.getReference(Organisation.class, dto.getOrgId()));
        return stud;
    }
}
