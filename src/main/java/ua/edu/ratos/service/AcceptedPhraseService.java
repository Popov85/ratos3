package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.AcceptedPhrase;
import ua.edu.ratos.domain.repository.AcceptedPhraseRepository;
import ua.edu.ratos.service.dto.entity.AcceptedPhraseInDto;
import ua.edu.ratos.service.dto.transformer.DtoAcceptedPhraseTransformer;

import java.util.List;

@Service
public class AcceptedPhraseService {

    @Autowired
    private AcceptedPhraseRepository acceptedPhraseRepository;

    @Autowired
    private DtoAcceptedPhraseTransformer transformer;

    @Autowired
    private PropertiesService propertiesService;


    @Transactional
    public Long save(@NonNull AcceptedPhraseInDto dto) {
        AcceptedPhrase phrase = transformer.fromDto(dto);
        return acceptedPhraseRepository.save(phrase).getPhraseId();
    }

    @Transactional
    public void update(@NonNull Long phraseId, @NonNull AcceptedPhraseInDto dto) {
        if (!acceptedPhraseRepository.existsById(phraseId))
            throw new RuntimeException("Failed to update phrase: ID does not exist");
        AcceptedPhrase phrase = transformer.fromDto(dto, phraseId);
        acceptedPhraseRepository.save(phrase);
    }

    @Transactional
    public void deleteById(@NonNull Long phraseId) {
        acceptedPhraseRepository.deleteById(phraseId);
    }

    /*-----------------------SELECT----------------------*/

    @Transactional(readOnly = true)
    public List<AcceptedPhrase> findAllLastUsedByStaffId(@NonNull Long staffId) {
        return acceptedPhraseRepository.findAllLastUsedByStaffId(staffId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<AcceptedPhrase> findAllLastUsedByStaffIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        return acceptedPhraseRepository.findAllLastUsedByStaffIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
    }

}
