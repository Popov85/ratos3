package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.domain.entity.answer.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.repository.AcceptedPhraseRepository;
import ua.edu.ratos.domain.repository.AnswerFBSQRepository;
import ua.edu.ratos.domain.repository.SettingsAnswerFillBlankRepository;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerFBSQService {

    @Autowired
    private AnswerFBSQRepository answerRepository;

    @Autowired
    private AcceptedPhraseRepository phraseRepository;

    @Autowired
    private SettingsAnswerFillBlankRepository settingsRepository;

    @Transactional
    public void addAcceptedPhrase(@NonNull AcceptedPhrase phrase, @NonNull Long answerId) {
        final AnswerFillBlankSingle answer = answerRepository.findByIdWithAcceptedPhrases(answerId);
        answer.addPhrase(phrase);
    }

    @Transactional
    public void addAcceptedPhrase(@NonNull Long phraseId, @NonNull Long answerId) {
        final AnswerFillBlankSingle answer = answerRepository.findByIdWithAcceptedPhrases(answerId);
        final AcceptedPhrase phrase = phraseRepository.findById(phraseId).get();
        phrase.setLastUsed(LocalDateTime.now());
        answer.addPhrase(phrase);
    }

    @Transactional
    public void deleteAcceptedPhrase(@NonNull Long phraseId, @NonNull Long answerId) {
        final AnswerFillBlankSingle answer = answerRepository.findByIdWithAcceptedPhrases(answerId);
        AcceptedPhrase deletablePhrase = phraseRepository.getOne(phraseId);
        answer.removePhrase(deletablePhrase);
    }

    /**
     * Effectively updates only settings
     * @param answerId
     * @param setId
     */
    @Transactional
    public void update(@NonNull Long answerId, @NonNull Long setId) {
        final Optional<AnswerFillBlankSingle> optional = answerRepository.findById(answerId);
        final AnswerFillBlankSingle oldAnswer = optional.orElseThrow(() ->
                new RuntimeException("AnswerFillBlankSingle not found, ID = "+ answerId));
        final SettingsAnswerFillBlank updatedSettings = settingsRepository.getOne(setId);
        oldAnswer.setSettings(updatedSettings);
    }
}
