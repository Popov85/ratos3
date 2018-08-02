package ua.edu.ratos.service;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.domain.repository.AcceptedPhraseRepository;
import ua.edu.ratos.domain.repository.AnswerFBMQRepository;
import ua.edu.ratos.domain.repository.QuestionFBMQRepository;
import ua.edu.ratos.domain.repository.SettingsAnswerFillBlankRepository;
import ua.edu.ratos.service.dto.entity.AnswerFBMQInDto;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AnswerFBMQService {

    @Autowired
    private AnswerFBMQRepository answerRepository;
    @Autowired
    private QuestionFBMQRepository questionRepository;
    @Autowired
    private SettingsAnswerFillBlankRepository settingsRepository;
    @Autowired
    private AcceptedPhraseRepository phraseRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public Long save(@NonNull AnswerFBMQInDto dto) {
        return answerRepository.save(fromDto(dto)).getAnswerId();
    }

    @Transactional
    public void update(@NonNull AnswerFBMQInDto dto) {
        answerRepository.save(fromDto(dto));
    }

    @Transactional
    public void addAcceptedPhrase(@NonNull Long answerId, @NonNull Long phraseId) {
        final AnswerFillBlankMultiple answer = answerRepository.findByIdWithAcceptedPhrases(answerId);
        // Will fetch the whole phrase in transaction by default
        final AcceptedPhrase phrase = phraseRepository.getOne(phraseId);
        phrase.setLastUsed(LocalDateTime.now());
        answer.addPhrase(phrase);
    }

    @Transactional
    public void deleteAcceptedPhrase(@NonNull Long answerId, @NonNull Long phraseId) {
        final AnswerFillBlankMultiple answer = answerRepository.findByIdWithAcceptedPhrases(answerId);
        AcceptedPhrase deletablePhrase = phraseRepository.getOne(phraseId);
        answer.removePhrase(deletablePhrase);
    }

    @Transactional
    public void deleteById(@NonNull Long answerId) {
        answerRepository.pseudoDeleteById(answerId);
    }


    private AnswerFillBlankMultiple fromDto(AnswerFBMQInDto dto) {
        if (dto.getPhrasesIds()==null||dto.getPhrasesIds().isEmpty())
            throw new RuntimeException("Answer does not make sense without any accepted phrases!");
        AnswerFillBlankMultiple answer = modelMapper.map(dto, AnswerFillBlankMultiple.class);
        answer.setQuestion(questionRepository.getOne(dto.getQuestionId()));
        answer.setSettings(settingsRepository.getOne(dto.getSetId()));
        Set<AcceptedPhrase> phrases = new HashSet<>();
        dto.getPhrasesIds().forEach(id->phrases.add(phraseRepository.getOne(id)));
        answer.setAcceptedPhrases(phrases);
        return answer;
    }
}
