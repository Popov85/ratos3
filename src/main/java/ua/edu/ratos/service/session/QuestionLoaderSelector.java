package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.session.sequence.QuestionLoader;
import ua.edu.ratos.service.session.sequence.QuestionLoaderFactory;

@Slf4j
@Service
public class QuestionLoaderSelector {

    private AppProperties appProperties;

    private QuestionLoaderFactory questionLoaderFactory;

    private QuestionLoaderResolver sequenceMapperResolver;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setQuestionLoaderFactory(QuestionLoaderFactory questionLoaderFactory) {
        this.questionLoaderFactory = questionLoaderFactory;
    }

    @Autowired
    public void setSequenceMapperResolver(QuestionLoaderResolver sequenceMapperResolver) {
        this.sequenceMapperResolver = sequenceMapperResolver;
    }

    public QuestionLoader select(@NonNull final Scheme scheme) {
        AppProperties.Session.Algorithm algorithm = appProperties.getSession().getRandomAlgorithm();
        if (algorithm.equals(AppProperties.Session.Algorithm.SIMPLE)) {
            log.debug("Selected simple implementation of question loader");
            return questionLoaderFactory.getInstance("simple");
        } else if (algorithm.equals(AppProperties.Session.Algorithm.CACHED)) {
            log.debug("Selected cached implementation of question loader");
            return questionLoaderFactory.getInstance("cached");
        } else if (algorithm.equals(AppProperties.Session.Algorithm.DECIDE)) {
            // Try to resolve
            return sequenceMapperResolver.resolve(scheme);
        } else {
            throw new UnsupportedOperationException("Wrong algorithm");
        }
    }
}
