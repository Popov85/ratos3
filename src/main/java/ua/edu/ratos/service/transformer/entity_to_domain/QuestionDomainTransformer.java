package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.domain.ThemeDomain;
import ua.edu.ratos.service.domain.question.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuestionDomainTransformer {

    private AnswerDomainTransformer answerDomainTransformer;

    private HelpDomainTransformer helpDomainTransformer;

    private ResourceDomainTransformer resourceDomainTransformer;

    @Autowired
    public void setAnswerDomainTransformer(AnswerDomainTransformer answerDomainTransformer) {
        this.answerDomainTransformer = answerDomainTransformer;
    }

    @Autowired
    public void setHelpDomainTransformer(HelpDomainTransformer helpDomainTransformer) {
        this.helpDomainTransformer = helpDomainTransformer;
    }

    @Autowired
    public void setResourceDomainTransformer(ResourceDomainTransformer resourceDomainTransformer) {
        this.resourceDomainTransformer = resourceDomainTransformer;
    }

    // Here opened earlier transaction comes into play
    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionDomain toDomain(@NonNull final Question entity) {
        if (entity.getType().getTypeId().equals(1L)) {
            return toDomain((QuestionMCQ) entity);
        } else if (entity.getType().getTypeId().equals(2L)) {
            return toDomain((QuestionFBSQ) entity);
        } else if (entity.getType().getTypeId().equals(3L)) {
            return toDomain((QuestionFBMQ) entity);
        } else if (entity.getType().getTypeId().equals(4L)) {
            return toDomain((QuestionMQ) entity);
        } else if (entity.getType().getTypeId().equals(5L)) {
            return toDomain((QuestionSQ) entity);
        } else throw new RuntimeException("Failed to transform Question to QuestionDomain");
    }

    public QuestionFBMQDomain toDomain(@NonNull final QuestionFBMQ entity) {
        QuestionFBMQDomain domain = new QuestionFBMQDomain();
        mapDomain(entity, domain);
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a-> domain.addAnswer(answerDomainTransformer.toDomain(a)));
        return domain;
    }

    public QuestionFBSQDomain toDomain(@NonNull final QuestionFBSQ entity) {
        QuestionFBSQDomain domain = new QuestionFBSQDomain();
        mapDomain(entity, domain);
        domain.setAnswer(answerDomainTransformer.toDomain(entity.getAnswer()));
        return domain;
    }

    public QuestionMCQDomain toDomain(@NonNull final QuestionMCQ entity) {
        QuestionMCQDomain domain = new QuestionMCQDomain();
        mapDomain(entity, domain);
        domain.setSingle(entity.isSingle());
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a-> domain.addAnswer(answerDomainTransformer.toDomain(a)));
        return domain;
    }

    public QuestionMQDomain toDomain(@NonNull final QuestionMQ entity) {
        QuestionMQDomain domain = new QuestionMQDomain();
        mapDomain(entity, domain);
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a -> domain.addAnswer(answerDomainTransformer.toDomain(a)));
        return domain;
    }

    public QuestionSQDomain toDomain(@NonNull final QuestionSQ entity) {
        QuestionSQDomain domain = new QuestionSQDomain();
        mapDomain(entity, domain);
        entity.getAnswers().forEach(a -> domain.add(answerDomainTransformer.toDomain(a)));
        return domain;
    }

    private void mapDomain(Question entity, QuestionDomain domain) {
        domain.setQuestionId(entity.getQuestionId());
        domain.setQuestion(entity.getQuestion());
        domain.setLevel(entity.getLevel());
        domain.setRequired(entity.isRequired());

        ThemeDomain themeDomain = new ThemeDomain();
        themeDomain.setThemeId(entity.getTheme().getThemeId());
        themeDomain.setName(entity.getTheme().getName());
        domain.setThemeDomain(themeDomain);

        domain.setLang(entity.getLang().getAbbreviation());
        domain.setType(entity.getType().getTypeId());

        domain.setHelpDomain((entity.getHelp().isPresent())
                ? helpDomainTransformer.toDomain(entity.getHelp().get()) : null);
        if (entity.getResources()!=null) {
            domain.setResourceDomains(entity.getResources()
                    .stream()
                    .map(resourceDomainTransformer::toDomain)
                    .collect(Collectors.toSet()));
        } else {
            domain.setResourceDomains(Collections.emptySet());
        }
        domain.setPartialResponseAllowed(entity.isPartialResponseAllowed());
    }
}
