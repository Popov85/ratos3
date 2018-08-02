package ua.edu.ratos.service;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.domain.entity.answer.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.repository.AcceptedPhraseRepository;
import ua.edu.ratos.domain.repository.AnswerFBSQRepository;
import ua.edu.ratos.domain.repository.SettingsAnswerFillBlankRepository;
import ua.edu.ratos.service.dto.entity.AnswerFBSQInDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AnswerFBSQService {

    @Autowired
    private AnswerFBSQRepository answerRepository;

    @Autowired
    private AcceptedPhraseRepository phraseRepository;

    @Autowired
    private SettingsAnswerFillBlankRepository settingsRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public void update(@NonNull AnswerFBSQInDto dto) {
        answerRepository.save(fromDto(dto));
    }

    @Transactional
    public void addAcceptedPhrase(@NonNull Long answerId, @NonNull Long phraseId) {
        final AnswerFillBlankSingle answer = answerRepository.findByIdWithAcceptedPhrases(answerId);
        final AcceptedPhrase phrase = phraseRepository.getOne(phraseId);
        phrase.setLastUsed(LocalDateTime.now());
        answer.addPhrase(phrase);
    }

    @Transactional
    public void deleteAcceptedPhrase(@NonNull Long answerId, @NonNull Long phraseId) {
        final AnswerFillBlankSingle answer = answerRepository.findByIdWithAcceptedPhrases(answerId);
        AcceptedPhrase deletablePhrase = phraseRepository.getOne(phraseId);
        answer.removePhrase(deletablePhrase);
    }

    private AnswerFillBlankSingle fromDto(AnswerFBSQInDto dto) {
        if (dto.getPhrasesIds()==null||dto.getPhrasesIds().isEmpty())
            throw new RuntimeException("Answer does not make sense without any accepted phrases!");
        AnswerFillBlankSingle answer = modelMapper.map(dto, AnswerFillBlankSingle.class);
        answer.setSettings(settingsRepository.getOne(dto.getSetId()));
        Set<AcceptedPhrase> phrases = new HashSet<>();
        dto.getPhrasesIds().forEach(id->phrases.add(phraseRepository.getOne(id)));
        answer.setAcceptedPhrases(phrases);
        return answer;
    }
}
