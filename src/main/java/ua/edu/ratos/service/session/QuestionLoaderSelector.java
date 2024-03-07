package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.session.sequence.QuestionLoader;
import ua.edu.ratos.service.session.sequence.QuestionLoaderFactory;

@Slf4j
@Service
@AllArgsConstructor
public class QuestionLoaderSelector {

    private final AppProperties appProperties;

    private final QuestionLoaderFactory questionLoaderFactory;

    private final QuestionLoaderResolver sequenceMapperResolver;


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
