package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.repository.PhraseRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.PhraseInDto;
import ua.edu.ratos.service.dto.out.PhraseOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoPhraseTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.PhraseDtoTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
@AllArgsConstructor
public class PhraseService {

    private static final String PHRASE_NOT_FOUND = "The requested phrase not found, phraseId = ";

    @PersistenceContext
    private final EntityManager em;

    private final PhraseRepository phraseRepository;

    private final DtoPhraseTransformer dtoPhraseTransformer;

    private final PhraseDtoTransformer phraseDtoTransformer;

    private final SecurityUtils securityUtils;

    //-----------------------------------------------------CRUD---------------------------------------------------------
    @Transactional
    public Long save(@NonNull final PhraseInDto dto) {
        Phrase phrase = dtoPhraseTransformer.toEntity(dto);
        return phraseRepository.save(phrase).getPhraseId();
    }

    @Transactional
    public void updatePhrase(@NonNull final Long phraseId, @NonNull final String phrase) {
        phraseRepository.findById(phraseId)
                .orElseThrow(() -> new EntityNotFoundException(PHRASE_NOT_FOUND + phraseId))
                .setPhrase(phrase);
    }

    @Transactional
    public void updateResource(@NonNull final Long phraseId, @NonNull final Long resId) {
        Phrase phrase = phraseRepository.findById(phraseId)
                .orElseThrow(() -> new EntityNotFoundException(PHRASE_NOT_FOUND + phraseId));
        phrase.clearResources();
        phrase.addResource(em.getReference(Resource.class, resId));
    }

    @Transactional
    public void deleteById(@NonNull final Long phraseId) {
        phraseRepository.findById(phraseId)
                .orElseThrow(() -> new EntityNotFoundException(PHRASE_NOT_FOUND + phraseId))
                .setDeleted(true);
    }

    //-------------------------------------------------One (for update)-------------------------------------------------
    @Transactional(readOnly = true)
    public PhraseOutDto findOneForUpdate(@NonNull final Long phraseId) {
        return phraseDtoTransformer.toDto(phraseRepository.findOneForEdit(phraseId)
                .orElseThrow(() -> new EntityNotFoundException(PHRASE_NOT_FOUND + phraseId)));
    }

    //----------------------------------------------------Staff table---------------------------------------------------
    @Transactional(readOnly = true)
    public Page<PhraseOutDto> findAllByStaffId(@NonNull final Pageable pageable) {
        return phraseRepository.findAllByStaffId(securityUtils.getAuthStaffId(), pageable).map(phraseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PhraseOutDto> findAllByDepartmentId(@NonNull final Pageable pageable) {
        return phraseRepository.findAllByDepartmentId(securityUtils.getAuthDepId(), pageable).map(phraseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PhraseOutDto> findAllByStaffIdAndPhraseLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return phraseRepository.findAllByStaffIdAndPhraseLettersContains(securityUtils.getAuthStaffId(), letters, pageable).map(phraseDtoTransformer::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PhraseOutDto> findAllByDepartmentIdAndPhraseLettersContains(@NonNull final String letters, @NonNull final Pageable pageable) {
        return phraseRepository.findAllByDepartmentIdAndPhraseLettersContains(securityUtils.getAuthDepId(), letters, pageable).map(phraseDtoTransformer::toDto);
    }

    //--------------------------------------------------------ADMIN-----------------------------------------------------
    @Transactional(readOnly = true)
    public Page<PhraseOutDto> findAll(@NonNull final Pageable pageable) {
        return phraseRepository.findAll(pageable).map(phraseDtoTransformer::toDto);
    }

}
