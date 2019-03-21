package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.domain.CreateData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.sequence.SequenceFactory;
import ua.edu.ratos.service.session.sequence.SequenceMapper;
import ua.edu.ratos.service.session.sequence.SequenceMapperFactory;
import ua.edu.ratos.service.session.sequence.SequenceProducer;
import ua.edu.ratos.service.transformer.entity_to_domain.QuestionDomainTransformer;
import ua.edu.ratos.service.transformer.entity_to_domain.SchemeDomainTransformer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SessionDataBuilder {

    private AppProperties appProperties;

    private SequenceFactory sequenceFactory;

    private SequenceMapperFactory sequenceMapperFactory;

    private TimingService timingService;

    private SchemeDomainTransformer schemeDomainTransformer;

    private QuestionDomainTransformer questionDomainTransformer;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setSequenceFactory(SequenceFactory sequenceFactory) {
        this.sequenceFactory = sequenceFactory;
    }

    @Autowired
    public void setSequenceMapperFactory(SequenceMapperFactory sequenceMapperFactory) {
        this.sequenceMapperFactory = sequenceMapperFactory;
    }

    @Autowired
    public void setTimingService(TimingService timingService) {
        this.timingService = timingService;
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
        String sequenceMapperImpl;
        AppProperties.Session.Algorithm algorithm = appProperties.getSession().getRandomAlgorithm();
        if (algorithm.equals(AppProperties.Session.Algorithm.JPA)) {
            sequenceMapperImpl = "jpa";
        } else if (algorithm.equals(AppProperties.Session.Algorithm.DB)) {
            sequenceMapperImpl = "db";
        } else {
            throw new UnsupportedOperationException("Intelligent algorithm resolver is not yet implemented!");
        }
        log.debug("Selected sequenceMapperImpl = {}", sequenceMapperImpl);
        SequenceMapper sequenceMapper = sequenceMapperFactory.getSequenceMapper(sequenceMapperImpl);
        SequenceProducer sequenceProducer = sequenceFactory.getSequenceProducer(scheme.getStrategy());
        List<Question> questions = sequenceProducer.getSequence(scheme.getThemes(), sequenceMapper);
        log.debug("Produced the sequence of total = {} question entities", questions.size());
        List<QuestionDomain> sequence = questions.stream().map(questionDomainTransformer::toDomain).collect(Collectors.toList());

        log.debug("Individual sequence of question domain objects is built of size = {} for the user = {} taking scheme ID = {}"
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
