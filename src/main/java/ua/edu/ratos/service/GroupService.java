package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.entity.StudentGroup;
import ua.edu.ratos.dao.entity.StudentGroupId;
import ua.edu.ratos.dao.repository.GroupRepository;
import ua.edu.ratos.dao.repository.StudentGroupRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.GroupInDto;
import ua.edu.ratos.service.dto.out.GroupExtendedOutDto;
import ua.edu.ratos.service.dto.out.GroupOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoGroupTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.GroupDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.GroupExtendedDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
public class GroupService {

    private static final String GROUP_NOT_FOUND = "The requested Group not found, groupId = ";

    @PersistenceContext
    private EntityManager em;

    private GroupRepository groupRepository;

    private DtoGroupTransformer dtoGroupTransformer;

    private GroupExtendedDtoTransformer groupExtendedDtoTransformer;

    private GroupDtoTransformer groupDtoTransformer;

    private SecurityUtils securityUtils;

    private StudentGroupRepository studentGroupRepository;

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Autowired
    public void setDtoGroupTransformer(DtoGroupTransformer dtoGroupTransformer) {
        this.dtoGroupTransformer = dtoGroupTransformer;
    }

    @Autowired
    public void setGroupExtendedDtoTransformer(GroupExtendedDtoTransformer groupExtendedDtoTransformer) {
        this.groupExtendedDtoTransformer = groupExtendedDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setStudentGroupRepository(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @Autowired
    public void setGroupDtoTransformer(GroupDtoTransformer groupDtoTransformer) {
        this.groupDtoTransformer = groupDtoTransformer;
    }

    //---------------------------------------------------------CRUD-----------------------------------------------------

    @Transactional
    public Long save(@NonNull final GroupInDto dto) {
        return groupRepository.save(dtoGroupTransformer.toEntity(dto)).getGroupId();
    }

    @Transactional
    public void updateName(@NonNull final Long groupId, @NonNull final String name) {
        groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException(GROUP_NOT_FOUND + groupId))
                .setName(name);
    }

    //---------------------------------------------------Students operations--------------------------------------------
    @Transactional
    public void addStudent(@NonNull final Long groupId, @NonNull final Long studentId) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setStudent(em.getReference(Student.class, studentId));
        studentGroup.setGroup(em.getReference(Group.class, groupId));
        studentGroupRepository.save(studentGroup);
    }

    @Transactional
    public void removeStudent(@NonNull final Long groupId, @NonNull final Long studentId) {
        studentGroupRepository.deleteById(new StudentGroupId(studentId, groupId));
    }

    @Transactional
    public void deleteById(@NonNull final Long groupId) {
        groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException(GROUP_NOT_FOUND + groupId))
                .setDeleted(true);
    }


    //-------------------------------------------------------One (for edit)---------------------------------------------

    @Transactional(readOnly = true)
    public GroupOutDto findOneForEdit(@NonNull final Long groupId) {
       return groupDtoTransformer.toDto(groupRepository.findOneForEdit(groupId));
    }

    //--------------------------------------------------------Staff table-----------------------------------------------

    @Transactional(readOnly = true)
    public Page<GroupExtendedOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return groupRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(groupExtendedDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<GroupExtendedOutDto> findAllByStaffIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return groupRepository.findAllByStaffIdAndNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(groupExtendedDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<GroupExtendedOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return groupRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(groupExtendedDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<GroupExtendedOutDto> findAllByDepartmentIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return groupRepository.findAllByDepartmentIdAndNameLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(groupExtendedDtoTransformer::toDto);
    }

    //--------------------------------------------------------ADMIN table-----------------------------------------------

    @Transactional(readOnly = true)
    public Page<GroupExtendedOutDto> findAllAdmin(@NonNull final Pageable pageable) {
        return groupRepository.findAllAdmin(pageable).map(groupExtendedDtoTransformer::toDto);
    }

}
