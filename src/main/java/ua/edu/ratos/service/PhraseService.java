package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.repository.PhraseRepository;
import ua.edu.ratos.service.dto.entity.PhraseInDto;
import ua.edu.ratos.service.dto.transformer.DtoPhraseTransformer;
import java.util.List;

@Service
public class PhraseService {

    @Autowired
    private PhraseRepository phraseRepository;

    @Autowired
    private DtoPhraseTransformer transformer;

    @Autowired
    private PropertiesService propertiesService;


    @Transactional
    public Long save(@NonNull PhraseInDto dto) {
        Phrase phrase = transformer.fromDto(dto);
        return phraseRepository.save(phrase).getPhraseId();
    }

    @Transactional
    public void update(@NonNull Long phraseId, @NonNull PhraseInDto dto) {
        if (!phraseRepository.existsById(phraseId))
            throw new RuntimeException("Failed to update phrase: ID does not exist");
        Phrase phrase = transformer.fromDto(dto, phraseId);
        phraseRepository.save(phrase);
    }

    @Transactional
    public void deleteById(@NonNull Long phraseId) {
        phraseRepository.deleteById(phraseId);
    }

    /*-----------------------SELECT----------------------*/

    @Transactional(readOnly = true)
    public List<Phrase> findAllLastUsedByStaffId(@NonNull Long staffId) {
        return phraseRepository.findAllLastUsedByStaffId(staffId, propertiesService.getInitCollectionSize()).getContent();
    }

    @Transactional(readOnly = true)
    public List<Phrase> findAllLastUsedByStaffIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        return phraseRepository.findAllLastUsedByStaffIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
    }

}
