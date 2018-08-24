package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
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

    @Transactional(readOnly = true)
    public List<AcceptedPhrase> findAllLastUsedByStaffId(@NonNull Long staffId) {
        return acceptedPhraseRepository.findAllLastUsedByStaffId(staffId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<AcceptedPhrase> findAllLastUsedByStaffIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        return acceptedPhraseRepository.findAllLastUsedByStaffIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional
    public void update(@NonNull AcceptedPhraseInDto dto) {
        if (dto.getPhraseId()==null || dto.getPhraseId()==0)
            throw new RuntimeException("Invalid AcceptedPhrase ID");
        AcceptedPhrase phrase = transformer.fromDto(dto);
        acceptedPhraseRepository.save(phrase);
    }

    @Transactional
    public void deleteById(@NonNull Long phraseId) {
        acceptedPhraseRepository.deleteById(phraseId);
    }
}
