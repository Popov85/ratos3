package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.repository.*;
import ua.edu.ratos.dao.repository.lms.LMSRepository;
import ua.edu.ratos.service.dto.in.StudentInDto;
import ua.edu.ratos.service.dto.out.ClassMinOutDto;
import ua.edu.ratos.service.dto.out.FacultyMinOutDto;
import ua.edu.ratos.service.dto.out.OrganisationMinOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoStudentTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.ClassMinDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.FacultyMinDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.OrganisationMinDtoTransformer;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SelfRegistrationService {

    private UserRepository userRepository;

    private StudentRepository studentRepository;

    private OrganisationRepository organisationRepository;

    private FacultyRepository facultyRepository;

    private ClassRepository classRepository;

    private OrganisationMinDtoTransformer organisationDtoTransformer;

    private ClassMinDtoTransformer classDtoTransformer;

    private FacultyMinDtoTransformer facultyDtoTransformer;

    private DtoStudentTransformer dtoStudentTransformer;

    private LMSRepository lmsRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setOrganisationRepository(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @Autowired
    public void setOrganisationDtoTransformer(OrganisationMinDtoTransformer organisationDtoTransformer) {
        this.organisationDtoTransformer = organisationDtoTransformer;
    }

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
    public void setClassDtoTransformer(ClassMinDtoTransformer classDtoTransformer) {
        this.classDtoTransformer = classDtoTransformer;
    }

    @Autowired
    public void setFacultyDtoTransformer(FacultyMinDtoTransformer facultyDtoTransformer) {
        this.facultyDtoTransformer = facultyDtoTransformer;
    }

    @Autowired
    public void setLmsRepository(LMSRepository lmsRepository) {
        this.lmsRepository = lmsRepository;
    }


    //---------------------------------------------------Self-registration----------------------------------------------
    @Transactional
    // TODO: Here comes logic to send email to provided address with information about password and link to RATOS
    public Long save(@NonNull final StudentInDto dto) {
        if (userRepository.findByEmail(dto.getUser().getEmail()).isPresent()) {
            throw new RuntimeException("Such email is already present in e-RATOS");
        }
        return studentRepository.save(dtoStudentTransformer.toEntity(dto)).getStudId();
    }

    //--------------------------------------------------SELECT for drop-down--------------------------------------------

    /**
     * We are able to derive organisation from LMS currently used
     */
    @Transactional(readOnly = true)
    public Long findOrganisation(@NonNull final Long lmsId) {
        Optional<LMS> lms = lmsRepository.findOneForRegById(lmsId);
        return lms.orElseThrow(()->new RuntimeException("LMS not found!")).getOrganisation().getOrgId();
    }

    /**
     * 1-2, max 4-5 organizations are expected per e-Ratos instance
     */
    @Transactional(readOnly = true)
    public Set<OrganisationMinOutDto> findAllOrganisations() {
        return organisationRepository
                .findAllForRegistration()
                .stream()
                .map(organisationDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    /**
     * Up to 20 faculties are expected per single organization
     *
     * @param orgId    orgId (optional param)
     * @return set of Faculty DTOs
     */
    @Transactional(readOnly = true)
    public Set<FacultyMinOutDto> findAllFacultiesByOrgId(@NonNull final Long orgId) {
        return facultyRepository
                .findAllByOrgId(orgId)
                .stream()
                .map(facultyDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    /**
     * Up to 100-200 classes are expected per single faculty
     *
     * @param facId    facId
     * @return set of Class DTOs
     */
    @Transactional(readOnly = true)
    public Set<ClassMinOutDto> findAllClassesByFacId(@NonNull final Long facId) {
        return classRepository
                .findAllByFacultyId(facId)
                .stream()
                .map(classDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }
}
