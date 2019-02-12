package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.repository.StaffRepository;
import ua.edu.ratos.dao.repository.UserRoleRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.*;
import ua.edu.ratos.service.dto.out.StaffOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoStaffTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.StaffDtoTransformer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
public class StaffService {

    private static final String STAFF_NOT_FOUND = "The requested Staff not found, staffId = ";

    @PersistenceContext
    private EntityManager em;

    private StaffRepository staffRepository;

    private UserRoleRepository userRoleRepository;

    private DtoStaffTransformer dtoStaffTransformer;

    private StaffDtoTransformer staffDtoTransformer;

    private SecurityUtils securityUtils;

    @Autowired
    public void setStaffRepository(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setDtoStaffTransformer(DtoStaffTransformer dtoStaffTransformer) {
        this.dtoStaffTransformer = dtoStaffTransformer;
    }

    @Autowired
    public void setStaffDtoTransformer(StaffDtoTransformer staffDtoTransformer) {
        this.staffDtoTransformer = staffDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //------------------------------------------------------CRUD--------------------------------------------------------

    // Save user of the same department
    // TODO: here comes logic to send email to provided address with information about password and link to RATOS
    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Long save(@NonNull final StaffInDto dto) {
        return staffRepository.save(dtoStaffTransformer.toEntity(dto)).getStaffId();
    }

    // Save user of another department
    // TODO: here comes logic to send email to provided address with information about password and link to RATOS
    @Transactional
    @Secured({"ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public Long save(@NonNull final StaffInDto dto, @NonNull final Long depId) {
        Staff entity = dtoStaffTransformer.toEntity(dto);
        entity.setDepartment(em.getReference(Department.class, depId));
        return staffRepository.save(entity).getStaffId();
    }

    // Can be done solely by user himself
    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updateNameAndSurname(@NonNull final Long staffId, @NonNull final UserMinInDto dto) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException(STAFF_NOT_FOUND));
        User user = staff.getUser();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
    }

    // Can be done solely by user himself
    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updateEmail(@NonNull final Long staffId, @NonNull final EmailInDto email) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException(STAFF_NOT_FOUND));
        User user = staff.getUser();
        user.setEmail(email.getEmail());
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updatePosition(@NonNull final Long staffId, @NonNull final  Long positionId) {
        staffRepository.findById(staffId).orElseThrow(()->new EntityNotFoundException(STAFF_NOT_FOUND))
                .setPosition(em.getReference(Position.class, positionId));
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void deactivate(@NonNull final Long staffId) {
        staffRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException(STAFF_NOT_FOUND))
                .getUser().setActive(false);
    }

    //-------------------------------------------------ROLEs management-------------------------------------------------

    // DEP-ADMIN: {ROLE_INSTRUCTOR (3), ROLE_LAB-ASSISTANT(4), ROLE_DEP-ADMIN(5)}
    @Transactional
    @Secured("ROLE_DEP-ADMIN")
    public void addRoleByDepAdmin(@NonNull final Long staffId, @NonNull final RoleByDepInDto dto) {
        UserRole userRole = new UserRole();
        userRole.setUser(em.getReference(User.class, staffId));
        userRole.setRole(em.getReference(Role.class, dto.getRoleId()));
        userRoleRepository.save(userRole);
    }

    // DEP-ADMIN: {ROLE_INSTRUCTOR (3), ROLE_LAB-ASSISTANT(4), ROLE_DEP-ADMIN(5)}
    @Transactional
    @Secured("ROLE_DEP-ADMIN")
    public void removeRoleByDepAdmin(@NonNull final Long staffId, @NonNull final RoleByDepInDto dto) {
        userRoleRepository.deleteById(new UserRoleId(staffId, dto.getRoleId()));
    }

    // GLOBAL-ADMIN: {ROLE_INSTRUCTOR (3), ROLE_LAB-ASSISTANT(4), ROLE_DEP-ADMIN(5), ROLE_FAC-ADMIN(6), ROLE_ORG-ADMIN(7)}
    @Transactional
    @Secured("ROLE_GLOBAL-ADMIN")
    public void addRoleByGlobal(@NonNull final Long staffId, @NonNull final RoleByGlobalInDto dto) {
        UserRole userRole = new UserRole();
        userRole.setUser(em.getReference(User.class, staffId));
        userRole.setRole(em.getReference(Role.class, dto.getRoleId()));
        userRoleRepository.save(userRole);
    }

    // GLOBAL-ADMIN: {ROLE_INSTRUCTOR (3), ROLE_LAB-ASSISTANT(4), ROLE_DEP-ADMIN(5), ROLE_FAC-ADMIN(6), ROLE_ORG-ADMIN(7)}
    @Transactional
    @Secured("ROLE_GLOBAL-ADMIN")
    public void removeRoleByGlobal(@NonNull final Long staffId, @NonNull final RoleByGlobalInDto dto) {
        userRoleRepository.deleteById(new UserRoleId(staffId, dto.getRoleId()));
    }

    //---------------------------------------------------One (for edit)-------------------------------------------------

    @Transactional(readOnly = true)
    public StaffOutDto findOneForEdit(@NonNull final Long staffId) {
        return staffDtoTransformer.toDto(staffRepository.findOneForEdit(staffId));
    }

    //-----------------------------------------------------DEP ADMIN----------------------------------------------------

    @Transactional(readOnly = true)
    public Page<StaffOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return staffRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(staffDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<StaffOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return staffRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(staffDtoTransformer::toDto);
    }

    //--------------------------------------------------------ADMIN-----------------------------------------------------

    @Transactional(readOnly = true)
    public Page<StaffOutDto> findAllAdmin(@NonNull final Pageable pageable) {
        return staffRepository.findAllAdmin(pageable).map(staffDtoTransformer::toDto);
    }
}
