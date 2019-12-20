package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Position;
import ua.edu.ratos.dao.entity.Role;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.RoleRepository;
import ua.edu.ratos.dao.repository.StaffRepository;
import ua.edu.ratos.dao.repository.UserRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.StaffInDto;
import ua.edu.ratos.service.dto.in.StaffUpdInDto;
import ua.edu.ratos.service.dto.out.StaffMinOutDto;
import ua.edu.ratos.service.dto.out.StaffOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoStaffTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.StaffDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.StaffMinDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Technically we should allow creating/editing users of org, fac, dep ONLY by corresponding admins,
 * but for the sake of simplicity we omit implementing this functionality, and thus we trust front-end
 * to deal with possible contentions!
 */
@Service
@AllArgsConstructor
public class StaffService {

    private static final String STAFF_NOT_FOUND = "The requested Staff is not found, staffId = ";

    @PersistenceContext
    private final EntityManager em;

    private final UserRepository userRepository;

    private final StaffRepository staffRepository;

    private final RoleRepository roleRepository;

    private final DtoStaffTransformer dtoStaffTransformer;

    private final StaffDtoTransformer staffDtoTransformer;

    private final StaffMinDtoTransformer staffMinDtoTransformer;


    private final SecurityUtils securityUtils;


    // Here comes logic to send email to provided address with password
    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Long save(@NonNull final StaffInDto dto) {
        Staff staff = dtoStaffTransformer.toEntity(dto);
        return staffRepository.save(staff).getStaffId();
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void update(@NonNull final StaffUpdInDto dto) {
        Long staffId = dto.getStaffId();
        if (staffId == null) throw new RuntimeException("Failed to update staff, staffId is required");
        Staff staff = staffRepository.findById(staffId).orElseThrow(() ->
                new EntityNotFoundException(STAFF_NOT_FOUND));
        User user = staff.getUser();
        user.setName(dto.getUser().getName());
        user.setSurname(dto.getUser().getSurname());
        user.setEmail(dto.getUser().getEmail());
        Role role = roleRepository.findByName(dto.getRole()).orElseThrow(()->
                new EntityNotFoundException("Role is not found, role = "+dto.getRole()));
        user.replaceRole(role);
        user.setActive(dto.isActive());
        staff.setPosition(em.getReference(Position.class, dto.getPositionId()));
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updateRole(@NonNull final Long staffId, @NonNull final String role) {
        // Check role change possibility!
        String authRole = securityUtils.getAuthRole();
        if (!securityUtils.isRoleGreater(authRole, role))
            throw new SecurityException("Role to be set is greater then your current role!");
        User user = userRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException(STAFF_NOT_FOUND));
        // TODO: Check if you can change role of this user?
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(role).orElseThrow(()->
                new EntityNotFoundException("ROLE is not found")))));
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updatePosition(@NonNull final Long staffId, @NonNull final Long positionId) {
        staffRepository.findById(staffId).orElseThrow(()->new EntityNotFoundException(STAFF_NOT_FOUND))
                .setPosition(em.getReference(Position.class, positionId));
    }

    //---------------------------------------------------One (for edit)-------------------------------------------------
    @Transactional(readOnly = true)
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public StaffOutDto findOneForEdit(@NonNull final Long staffId) {
        Staff staff = staffRepository.findOneForEdit(staffId)
                .orElseThrow(() -> new EntityNotFoundException("Staff is not found, staffId = " + staffId));
        return staffDtoTransformer.toDto(staff);
    }

    //---------------------------------------------------One (min for DTOs)---------------------------------------------
    @Transactional(readOnly = true)
    public StaffMinOutDto findOneForDto(@NonNull final Long staffId) {
        Staff staff = staffRepository.findOneForEdit(staffId)
                .orElseThrow(() -> new EntityNotFoundException("Staff is not found, staffId = " + staffId));
        return staffMinDtoTransformer.toDto(staff);
    }

    //---------------------------------------------------ALL for ADMIN-s------------------------------------------------
    @Transactional(readOnly = true)
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Set<StaffOutDto> findAllByDepartmentId() {
        return staffRepository.findAllByDepartmentId(securityUtils.getAuthDepId())
                .stream()
                .map(staffDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Secured({"ROLE_FAC-ADMIN","ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Set<StaffOutDto> findAllByFacultyId() {
        return staffRepository.findAllByFacultyId(securityUtils.getAuthFacId())
                .stream()
                .map(staffDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    @Secured({"ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Set<StaffOutDto> findAllByOrganisationId() {
        return staffRepository.findAllByOrganisationId(securityUtils.getAuthOrgId())
                .stream()
                .map(staffDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Secured({"ROLE_GLOBAL-ADMIN"})
    public Set<StaffOutDto> findAllByRatos() {
        return staffRepository.findAllByRatos()
                .stream()
                .map(staffDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }


    //---------------------------------------------SOLELY for future references-----------------------------------------

    @Transactional(readOnly = true)
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Page<StaffOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return staffRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(staffDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Page<StaffOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return staffRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(staffDtoTransformer::toDto);
    }

    //--------------------------------------------------------ADMIN-----------------------------------------------------
    @Transactional(readOnly = true)
    @Secured({"ROLE_GLOBAL-ADMIN"})
    public Page<StaffOutDto> findAllAdmin(@NonNull final Pageable pageable) {
        return staffRepository.findAllAdmin(pageable).map(staffDtoTransformer::toDto);
    }

}
