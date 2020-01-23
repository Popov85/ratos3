package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.lms.LTICredentials;
import ua.edu.ratos.dao.entity.lms.LTIVersion;
import ua.edu.ratos.dao.repository.lms.LMSRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.LMSInDto;
import ua.edu.ratos.service.dto.out.CourseMinOutDto;
import ua.edu.ratos.service.dto.out.LMSMinOutDto;
import ua.edu.ratos.service.dto.out.LMSOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoLMSTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.LMSDtoTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.LMSMinDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LMSService {

    private static final String LMS_NOT_FOUND = "The requested LMS is not found, lmsId = ";

    @PersistenceContext
    private final EntityManager em;

    private final LMSRepository lmsRepository;

    private final DtoLMSTransformer dtoLMSTransformer;

    private final LMSDtoTransformer lmsDtoTransformer;

    private final LMSMinDtoTransformer lmsMinDtoTransformer;


    private final SecurityUtils securityUtils;

    //----------------------------------------------------CRUD----------------------------------------------------------
    @Transactional
    public Long save(@NonNull final LMSInDto dto) {
        return lmsRepository.save(dtoLMSTransformer.toEntity(dto)).getLmsId();
    }

    @Transactional
    public void update (@NonNull final LMSInDto dto) {
        if (dto.getLmsId()==null)
            throw new RuntimeException("Failed to update, lmsId is nullable");
        lmsRepository.save(dtoLMSTransformer.toEntity(dto));
        return;
    }

    @Transactional
    public void updateName(@NonNull final Long lmsId, @NonNull final String name) {
        lmsRepository.findById(lmsId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_NOT_FOUND + lmsId))
                .setName(name);
    }

    @Transactional
    public void updateVersion(@NonNull final Long lmsId, @NonNull final Long versionId) {
        lmsRepository.findById(lmsId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_NOT_FOUND + lmsId))
                .setLtiVersion(em.getReference(LTIVersion.class, versionId));
    }

    @Transactional
    public void updateCredentials(@NonNull final Long lmsId, @NonNull final String key, @NonNull final String secret) {
        LTICredentials credentials = lmsRepository.findById(lmsId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_NOT_FOUND + lmsId))
                .getCredentials();
        credentials.setKey(key);
        credentials.setSecret(secret);
    }

    @Transactional
    public void delete(@NonNull final Long lmsId) {
        lmsRepository.findById(lmsId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_NOT_FOUND + lmsId))
                .setDeleted(true);
    }

    //-----------------------------------------------------Staff drop-down----------------------------------------------

    @Transactional(readOnly = true)
    public Set<LMSMinOutDto> findAllForDropdownByOrganisation() {
        return lmsRepository.findAllForDropdownByOrgId(securityUtils.getAuthOrgId())
                .stream()
                .map(lmsMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //-----------------------------------------------------ORG admin table----------------------------------------------

    @Transactional(readOnly = true)
    public Set<LMSOutDto> findAllForTableByOrganisation() {
        return lmsRepository.findAllForTableByOrgId(securityUtils.getAuthOrgId())
                .stream()
                .map(lmsDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<LMSOutDto> findAllForTableByOrganisationId(@NonNull final Long orgId) {
        return lmsRepository.findAllForDropdownByOrgId(orgId)
                .stream()
                .map(lmsDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //--------------------------------------------------------ADMIN table-----------------------------------------------
    @Transactional(readOnly = true)
    public Page<LMSOutDto> findAllAdmin(@NonNull final Pageable pageable) {
        return lmsRepository.findAllAdmin(pageable).map(lmsDtoTransformer::toDto);
    }
}
