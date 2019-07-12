package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.ClassRepository;
import ua.edu.ratos.dao.repository.FacultyRepository;
import ua.edu.ratos.dao.repository.StudentRepository;
import ua.edu.ratos.dao.repository.lms.LMSRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.StudentInDto;
import ua.edu.ratos.service.dto.out.ClassMinOutDto;
import ua.edu.ratos.service.dto.out.FacultyMinOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoStudentTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ClassMinDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.FacultyMinDtoTransformer;

@Service
public class LMSSelfRegistrationService {

    private StudentRepository studentRepository;

    private DtoStudentTransformer dtoStudentTransformer;

    private FacultyRepository facultyRepository;

    private ClassRepository classRepository;

    private ClassMinDtoTransformer classMinDtoTransformer;

    private FacultyMinDtoTransformer facultyMinDtoTransformer;

    private SecurityUtils securityUtils;

    private LMSRepository lmsRepository;


    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setDtoStudentTransformer(DtoStudentTransformer dtoStudentTransformer) {
        this.dtoStudentTransformer = dtoStudentTransformer;
    }

    @Autowired
    public void setFacultyRepository(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Autowired
    public void setClassRepository(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Autowired
    public void setClassMinDtoTransformer(ClassMinDtoTransformer classMinDtoTransformer) {
        this.classMinDtoTransformer = classMinDtoTransformer;
    }

    @Autowired
    public void setFacultyMinDtoTransformer(FacultyMinDtoTransformer facultyMinDtoTransformer) {
        this.facultyMinDtoTransformer = facultyMinDtoTransformer;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setLmsRepository(LMSRepository lmsRepository) {
        this.lmsRepository = lmsRepository;
    }

    //------------------------------------------------LMS self-registration--------------------------------------------
    @Transactional
    // Here comes logic to send email to provided address with information about password and link to RATOS
    public Long save(@NonNull final StudentInDto dto) {
        return studentRepository.save(dtoStudentTransformer.toEntity(dto)).getStudId();
    }

    //--------------------------------------------------SELECT for drop-down--------------------------------------------

    @Transactional(readOnly = true)
    public Slice<FacultyMinOutDto> findAllFacultiesByOrgId(@NonNull final Pageable pageable) {
        // Find orgId by lmsId?
        Long lmsId = securityUtils.getLmsId();
        Long orgId = lmsRepository.findById(lmsId).get().getOrganisation().getOrgId();
        return facultyRepository.findAllByOrgId(orgId, pageable).map(facultyMinDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<ClassMinOutDto> findAllClassesByFacId(@NonNull final Long facId, @NonNull final Pageable pageable) {
        return classRepository.findAllByFacultyId(facId, pageable).map(classMinDtoTransformer::toDto);
    }
}
