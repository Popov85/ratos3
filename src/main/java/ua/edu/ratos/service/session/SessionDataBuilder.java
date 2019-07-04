package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.Strategy;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.domain.CreateData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.sequence.QuestionLoader;
import ua.edu.ratos.service.session.sequence.SequenceFactory;
import ua.edu.ratos.service.session.sequence.SequenceProducer;
import ua.edu.ratos.service.transformer.entity_to_domain.QuestionDomainTransformer;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SessionDataBuilder {

    private QuestionLoaderSelector questionLoaderSelector;

    private SequenceFactory sequenceFactory;

    private SchemeDomainTransformer schemeDomainTransformer;

    private QuestionDomainTransformer questionDomainTransformer;

    @Autowired
    public void setQuestionLoaderSelector(QuestionLoaderSelector questionLoaderSelector) {
        this.questionLoaderSelector = questionLoaderSelector;
    }

    @Autowired
    public void setSequenceFactory(SequenceFactory sequenceFactory) {
        this.sequenceFactory = sequenceFactory;
    }

    @Autowired
    public void setSchemeDomainTransformer(SchemeDomainTransformer schemeDomainTransformer) {
        this.schemeDomainTransformer = schemeDomainTransformer;
    }

    @Autowired
    public void setQuestionDomainTransformer(QuestionDomainTransformer questionDomainTransformer) {
        this.questionDomainTransformer = questionDomainTransformer;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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
        QuestionLoader questionLoader = questionLoaderSelector.select(scheme);
        Strategy strategy = scheme.getStrategy();
        log.debug("Strategy = {}", strategy.getName());
        SequenceProducer sequenceProducer = sequenceFactory.getSequenceProducer(strategy);
        List<Question> questions = sequenceProducer.getSequence(scheme, questionLoader);
        log.debug("Produced the sequence of total = {} question entities, strategy = {}", questions.size(), strategy.getName());
        List<QuestionDomain> sequence = questions.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toList());
        log.debug("Individual sequence of question domain objects is built of size = {} for the userId = {} taking schemeId = {}"
                ,sequence.size(), userId, scheme.getSchemeId());

        // Time control processing
        final int secondsPerQuestion = scheme.getSettings().getSecondsPerQuestion();
        final boolean strictControlTimePerQuestion = scheme.getSettings().isStrictControlTimePerQuestion();
        final LocalDateTime sessionTimeOut = TimingService.getSessionTimeOut(sequence.size(), secondsPerQuestion);
        final long questionTimeOut = TimingService.getQuestionTimeOut(secondsPerQuestion, strictControlTimePerQuestion);

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
