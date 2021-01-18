package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.repository.RoleRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.StaffInDto;
import ua.edu.ratos.service.dto.in.StaffUpdInDto;
import ua.edu.ratos.service.transformer.UserMapper;
import ua.edu.ratos.service.transformer.UserUpdMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Deprecated
@Component
@AllArgsConstructor
public class DtoStaffTransformer {

    @PersistenceContext
    private final EntityManager em;

    private final UserMapper userMapper;

    private final UserUpdMapper userUpdMapper;

    private final RoleRepository roleRepository;

    private final SecurityUtils securityUtils;


    // For new
    @Transactional(propagation = Propagation.MANDATORY)
    public Staff toEntity(@NonNull final StaffInDto dto) {
        Staff staff = new Staff();
        staff.setStaffId(dto.getStaffId());
        User user = userMapper.toEntity(dto.getUser());
        Optional<Role> role = roleRepository.findByName(dto.getRole());
        user.setRoles(new HashSet<>(Arrays.asList(role.orElseThrow(()->
                new EntityNotFoundException("ROLE is not found!")))));
        user.setActive(dto.isActive());
        staff.setUser(user);
        staff.setPosition(em.getReference(Position.class, dto.getPositionId()));
        // If depId is included then transform, if not - current admin depId
        staff.setDepartment(em.getReference(Department.class, dto.getDepId()
                .orElse(securityUtils.getAuthDepId())));
        return staff;
    }

    // For updates
    @Transactional(propagation = Propagation.MANDATORY)
    public Staff toEntity(@NonNull final Staff staff, @NonNull final StaffUpdInDto dto) {
        staff.setStaffId(dto.getStaffId());
        User user = userUpdMapper.toEntity(staff.getUser(), dto.getUser());
        Optional<Role> role = roleRepository.findByName(dto.getRole());
        user.setRoles(new HashSet<>(Arrays.asList(role.orElseThrow(()->
                new EntityNotFoundException("ROLE is not found!")))));
        user.setActive(dto.isActive());
        staff.setPosition(em.getReference(Position.class, dto.getPositionId()));
        // If depId is included then transform, if not - current admin depId
        staff.setDepartment(em.getReference(Department.class, dto.getDepId()
                .orElse(securityUtils.getAuthDepId())));
        return staff;
    }
}
