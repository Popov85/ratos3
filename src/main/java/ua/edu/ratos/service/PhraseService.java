package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.repository.PhraseRepository;
import ua.edu.ratos.service.dto.in.PhraseInDto;
import ua.edu.ratos.service.dto.out.PhraseOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoPhraseTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.PhraseDtoTransformer;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhraseService {

    @Autowired
    private PhraseRepository phraseRepository;

    @Autowired
    private DtoPhraseTransformer dtoPhraseTransformer;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private PhraseDtoTransformer phraseDtoTransformer;


    @Transactional
    public Long save(@NonNull PhraseInDto dto) {
        Phrase phrase = dtoPhraseTransformer.toEntity(dto);
        return phraseRepository.save(phrase).getPhraseId();
    }

    @Transactional
    public void update(@NonNull Long phraseId, @NonNull PhraseInDto dto) {
        if (!phraseRepository.existsById(phraseId))
            throw new RuntimeException("Failed to update the Phrase: ID does not exist");
        Phrase phrase = dtoPhraseTransformer.toEntity(dto, phraseId);
        phraseRepository.save(phrase);
    }

    @Transactional
    public void deleteById(@NonNull Long phraseId) {
        phraseRepository.deleteById(phraseId);
    }



    /*-----------------------SELECT----------------------*/


    @Transactional(readOnly = true)
    public Page<PhraseOutDto> findAll(@NonNull Pageable pageable) {
        Page<Phrase> page = phraseRepository.findAll(pageable);
        return new PageImpl<>(toDto(page.getContent()), pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<PhraseOutDto> findAllLastUsedByStaffId(@NonNull Long staffId) {
        List<Phrase> content = phraseRepository.findAllLastUsedByStaffId(staffId, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<PhraseOutDto> findAllLastUsedByDepId(@NonNull Long depId) {
        List<Phrase> content = phraseRepository.findAllLastUsedByDepId(depId, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<PhraseOutDto> findAllLastUsedByStaffIdAndFirstLetters(@NonNull Long staffId, @NonNull String starts) {
        List<Phrase> content = phraseRepository.findAllLastUsedByStaffIdAndFirstLetters(staffId, starts, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    @Transactional(readOnly = true)
    public List<PhraseOutDto> findAllLastUsedByDepIdAndFirstLetters(@NonNull Long depId, @NonNull String starts) {
        List<Phrase> content = phraseRepository.findAllLastUsedByDepIdAndFirstLetters(depId, starts, propertiesService.getInitCollectionSize()).getContent();
        return toDto(content);
    }

    private List<PhraseOutDto> toDto(@NonNull final List<Phrase> content) {
        return content.stream().map(phraseDtoTransformer::toDto).collect(Collectors.toList());
    }

}
