package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.domain.ThemeDomain;
import ua.edu.ratos.service.domain.question.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
@Component
public  class QuestionDomainTransformer {

    @Autowired
    private AnswerDomainTransformer answerDomainTransformer;

    @Autowired
    private HelpDomainTransformer helpDomainTransformer;

    @Autowired
    private ResourceDomainTransformer resourceDomainTransformer;

    public QuestionFBMQDomain toDomain(@NonNull final QuestionFBMQ entity) {
        QuestionFBMQDomain domain = new QuestionFBMQDomain();
        mapDomain(entity, domain);
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a-> domain.addAnswer(answerDomainTransformer.toDomain(a)));
        log.debug("Converted FBMQ toDomain = {}", domain);
        return domain;
    }

    public QuestionFBSQDomain toDomain(@NonNull final QuestionFBSQ entity) {
        QuestionFBSQDomain domain = new QuestionFBSQDomain();
        mapDomain(entity, domain);
        domain.setAnswer(answerDomainTransformer.toDomain(entity.getAnswer()));
        log.debug("Converted FBSQ toDomain = {}", domain);
        return domain;
    }

    public QuestionMCQDomain toDomain(@NonNull final QuestionMCQ entity) {
        QuestionMCQDomain domain = new QuestionMCQDomain();
        mapDomain(entity, domain);
        domain.setSingle(entity.isSingle());
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a-> domain.addAnswer(answerDomainTransformer.toDomain(a)));
        log.debug("Converted MCQ toDomain = {}", domain);
        return domain;
    }

    public QuestionMQDomain toDomain(@NonNull final QuestionMQ entity) {
        QuestionMQDomain domain = new QuestionMQDomain();
        mapDomain(entity, domain);
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a -> domain.addAnswer(answerDomainTransformer.toDomain(a)));
        log.debug("Converted MQ toDomain = {}", domain);
        return domain;
    }

    public QuestionSQDomain toDomain(@NonNull final QuestionSQ entity) {
        QuestionSQDomain domain = new QuestionSQDomain();
        mapDomain(entity, domain);
        entity.getAnswers().forEach(a -> domain.add(answerDomainTransformer.toDomain(a)));
        log.debug("Converted SQ toDomain = {}", domain);
        return domain;
    }


    private void mapDomain(Question entity, QuestionDomain domain) {
        domain.setQuestionId(entity.getQuestionId());
        domain.setQuestion(entity.getQuestion());
        domain.setLevel(entity.getLevel());

        ThemeDomain themeDomain = new ThemeDomain();
        themeDomain.setThemeId(entity.getTheme().getThemeId());
        themeDomain.setName(entity.getTheme().getName());
        domain.setThemeDomain(themeDomain);

        domain.setLang(entity.getLang().getAbbreviation());
        domain.setType(entity.getType().getTypeId());

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
    }
    
}
