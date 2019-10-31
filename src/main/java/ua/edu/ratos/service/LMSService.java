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
import ua.edu.ratos.service.dto.out.LMSOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoLMSTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.LMSDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
@AllArgsConstructor
public class LMSService {

    private static final String LMS_NOT_FOUND = "The requested LMS is not found, lmsId = ";

    @PersistenceContext
    private final EntityManager em;

    private final LMSRepository lmsRepository;

    private final DtoLMSTransformer dtoLMSTransformer;

    private final LMSDtoTransformer lmsDtoTransformer;

    private final SecurityUtils securityUtils;

    //----------------------------------------------------CRUD----------------------------------------------------------
    @Transactional
    public Long save(@NonNull final LMSInDto dto) {
        return lmsRepository.save(dtoLMSTransformer.toEntity(dto)).getLmsId();
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

    //-----------------------------------------------------One (for edit)-----------------------------------------------
    @Transactional(readOnly = true)
    public LMSOutDto findOneForEdit(@NonNull final Long lmsId) {
        return lmsDtoTransformer.toDto(lmsRepository.findOneForEditById(lmsId)
                .orElseThrow(() -> new EntityNotFoundException(LMS_NOT_FOUND + lmsId)));
    }

    //-----------------------------------------------------ORG admin table----------------------------------------------
    @Transactional(readOnly = true)
    public Slice<LMSOutDto> findAllByOrgId(@NonNull final Pageable pageable) {
        return lmsRepository.findAllByOrgId(securityUtils.getAuthOrgId(), pageable).map(lmsDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Slice<LMSOutDto> findAllByOrgIdAndNameLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return lmsRepository.findAllByOrgIdAndNameLettersContains(securityUtils.getAuthOrgId(), letters, pageable).map(lmsDtoTransformer::toDto);
    }

    //--------------------------------------------------------ADMIN table-----------------------------------------------
    @Transactional(readOnly = true)
    public Page<LMSOutDto> findAllAdmin(@NonNull final Pageable pageable) {
        return lmsRepository.findAllAdmin(pageable).map(lmsDtoTransformer::toDto);
    }
}
