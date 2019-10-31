package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.StudentRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.StudentInDto;
import ua.edu.ratos.service.dto.out.StudOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoStudentTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.StudDtoTransformer;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class StudentService {

    private static final String STUDENT_NOT_FOUND = "The requested Student is not found, studId = ";

    private final StudentRepository studentRepository;

    private final DtoStudentTransformer dtoStudentTransformer;

    private final StudDtoTransformer studDtoTransformer;

    private final SecurityUtils securityUtils;

    //---------------------------------------------------REGISTRATION by staff------------------------------------------
    @Transactional
    @Secured({"ROLE_LAB-ASSISTANT", "ROLE_INSTRUCTOR", "ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    // TODO: here comes logic to send email to provided address with information about password and link to RATOS
    public Long save(@NonNull final StudentInDto dto) {
        return studentRepository.save(dtoStudentTransformer.toEntity(dto)).getStudId();
    }

    //------------------------------------------------DEACTIVATE (expelled/graduated)-----------------------------------
    @Transactional
    @Secured({"ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void deactivate(@NonNull final Long studId) {
        studentRepository.findById(studId)
                .orElseThrow(() -> new EntityNotFoundException(STUDENT_NOT_FOUND + studId))
                .getUser()
                .setActive(false);
    }

    //--------------------------------------------------------One (for edit)--------------------------------------------
    @Transactional(readOnly = true)
    public StudOutDto findOneForEdit(@NonNull final Long studId) {
        return studDtoTransformer.toDto(studentRepository.findOneForEdit(studId).
                orElseThrow(() -> new EntityNotFoundException(STUDENT_NOT_FOUND + studId)));
    }

    //---------------------------------------------------------Staff table----------------------------------------------
    @Transactional(readOnly = true)
    public Page<StudOutDto> findAllByOrganisationId(@NonNull final Pageable pageable) {
        return studentRepository.findAllByOrgId(securityUtils.getAuthOrgId(), pageable).map(studDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<StudOutDto> findAllByOrganisationIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return studentRepository.findAllByOrgIdAndNameLettersContains(securityUtils.getAuthOrgId(), letters, pageable).map(studDtoTransformer::toDto);
    }

    //-------------------------------------------------------------ADMIN------------------------------------------------
    @Transactional(readOnly = true)
    public Page<StudOutDto> findAllAdmin(@NonNull final Pageable pageable) {
        return studentRepository.findAllAdmin(pageable).map(studDtoTransformer::toDto);
    }
}
