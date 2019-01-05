package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.domain.CreateData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.sequence.SequenceFactory;
import ua.edu.ratos.service.session.sequence.SequenceProducer;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class SessionDataBuilder {

    @Autowired
    private SequenceFactory sequenceFactory;

    @Autowired
    private TimingService timingService;

    @Autowired
    private SchemeDomainTransformer schemeDomainTransformer;


    public SessionData build(@NonNull final String key, @NonNull final Long userId, @NonNull final Scheme scheme) {
        CreateData createData = getCreateData(userId, scheme);
        // Build SessionData (no LMS)
        return new SessionData.Builder()
                .withKey(key)
                .forUser(userId)
                .noLMS()
                .takingScheme(createData.getSchemeDomain())
                .withIndividualSequence(createData.getSequence())
                .withQuestionsPerBatch(createData.getQuestionsPerBatch())
                .withSessionTimeout(createData.getSessionTimeOut())
                .withPerQuestionTimeLimit(createData.getQuestionTimeOut())
                .build();
    }

    public SessionData build(@NonNull final String key, @NonNull final Long userId, @NonNull final Scheme scheme, @NonNull final Long lmsId) {
        CreateData createData = getCreateData(userId, scheme);
        // Build SessionData (within LMS)
        return new SessionData.Builder()
                .withKey(key)
                .forUser(userId)
                .fromLMS(lmsId)
                .takingScheme(createData.getSchemeDomain())
                .withIndividualSequence(createData.getSequence())
                .withQuestionsPerBatch(createData.getQuestionsPerBatch())
                .withSessionTimeout(createData.getSessionTimeOut())
                .withPerQuestionTimeLimit(createData.getQuestionTimeOut())
                .build();
    }

    private CreateData getCreateData(@NonNull final Long userId, @NonNull final Scheme scheme) {
        final SequenceProducer sequenceProducer = sequenceFactory.getSequenceProducer(scheme.getStrategy());
        final List<QuestionDomain> sequence = sequenceProducer.getSequence(scheme.getThemes());
        log.debug("Individual sequence of questions is built of size = {} for the user = {} taking scheme ID = {}"
                ,sequence.size(), userId, scheme.getSchemeId());
        // Time control processing
        final int secondsPerQuestion = scheme.getSettings().getSecondsPerQuestion();
        final boolean strictControlTimePerQuestion = scheme.getSettings().isStrictControlTimePerQuestion();
        final LocalDateTime sessionTimeOut = timingService.getSessionTimeOut(sequence.size(), secondsPerQuestion);
        final long questionTimeOut = timingService.getQuestionTimeOut(secondsPerQuestion, strictControlTimePerQuestion);

        // Batch processing
        final int questionsTotal = sequence.size();
        final short questionsPerSheet = scheme.getSettings().getQuestionsPerSheet();
        final int questionsPerBatch = (questionsPerSheet <= 0 || questionsTotal <= questionsPerSheet) ? questionsTotal : questionsPerSheet;

        return new CreateData()
                .setSchemeDomain(schemeDomainTransformer.toDomain(scheme))
                .setSequence(sequence)
                .setQuestionsPerBatch(questionsPerBatch)
                .setQuestionTimeOut(questionTimeOut)
                .setSessionTimeOut(sessionTimeOut);
    }
}
