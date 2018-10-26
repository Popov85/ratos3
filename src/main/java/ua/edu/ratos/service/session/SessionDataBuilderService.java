package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.sequence.SequenceFactory;
import ua.edu.ratos.service.session.sequence.SequenceProducer;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class SessionDataBuilderService {

    @Autowired
    private SequenceFactory sequenceFactory;

    @Autowired
    private TimingService timingService;

    public SessionData build(@NonNull String key, @NonNull Long userId, @NonNull Scheme scheme) {

        // Obtain individual sequence of questions
        final SequenceProducer sequenceProducer = sequenceFactory.getSequenceProducer(scheme.getStrategy());
        final List<Question> sequence = sequenceProducer.getSequence(scheme.getSchemeThemes());
        log.debug("Individual sequence is built of size :: {}", sequence.size());

        // Time control processing
        final int secondsPerQuestion = scheme.getSettings().getSecondsPerQuestion();
        final boolean strictControlTimePerQuestion = scheme.getSettings().isStrictControlTimePerQuestion();
        final LocalDateTime sessionTimeOut = timingService.getSessionTimeOut(sequence.size(), secondsPerQuestion);
        final long questionTimeOut = timingService.getQuestionTimeOut(secondsPerQuestion, strictControlTimePerQuestion);

        // Batch processing
        final int questionsTotal = sequence.size();
        final short questionsPerSheet = scheme.getSettings().getQuestionsPerSheet();
        final int questionsPerBatch = (questionsPerSheet <= 0 || questionsTotal <= questionsPerSheet) ? questionsTotal : questionsPerSheet;

        // Build a SessionData
        return new SessionData.Builder()
                .withKey(key)
                .forUser(userId)
                .takingScheme(scheme)
                .withIndividualSequence(sequence)
                .withQuestionsPerBatch(questionsPerBatch)
                .withSessionTimeout(sessionTimeOut)
                .withPerQuestionTimeLimit(questionTimeOut)
                .build();
    }
}
