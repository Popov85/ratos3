package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.repository.StudentRepository;
import ua.edu.ratos.dao.repository.UserRepository;
import ua.edu.ratos.dao.repository.lms.LMSRepository;
import ua.edu.ratos.service.dto.in.StudentInDto;
import ua.edu.ratos.service.dto.out.ClassMinOutDto;
import ua.edu.ratos.service.dto.out.FacultyMinOutDto;
import ua.edu.ratos.service.dto.out.OrganisationMinOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoStudentTransformer;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SelfRegistrationService {

    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    private final OrganisationService organisationService;

    private final FacultyService facultyService;

    private final ClazzService classService;

    private final DtoStudentTransformer dtoStudentTransformer;

    private final LMSRepository lmsRepository;


    //---------------------------------------------------Self-registration----------------------------------------------
    @Transactional
    // TODO: Here comes logic to send email to provided address with information about password and link to RATOS
    public Long save(@NonNull final StudentInDto dto) {
        String email = dto.getUser().getEmail();
        if (userRepository.findByEmail(email).isPresent())
                throw new RuntimeException("Such email is already present in e-RATOS");
        Student entity = dtoStudentTransformer.toEntity(dto);
        return studentRepository.save(entity).getStudId();
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
        return organisationService.findAllOrganisationsForDropDown();
    }

    /**
     * Up to 20 faculties are expected per single organization;
     * @param orgId orgId (optional param)
     * @return set of Faculty DTOs
     */
    @Transactional(readOnly = true)
    public Set<FacultyMinOutDto> findAllFacultiesByOrgId(@NonNull final Long orgId) {
        return facultyService.findAllByOrgIdForDropDown(orgId);
    }

    /**
     * Up to 100-200 classes are expected per single faculty
     * @param facId facId
     * @return set of Class DTOs
     */
    @Transactional(readOnly = true)
    public Set<ClassMinOutDto> findAllClassesByFacId(@NonNull final Long facId) {
        return classService.findAllClassesByFacIdForDropDown(facId);
    }
}
