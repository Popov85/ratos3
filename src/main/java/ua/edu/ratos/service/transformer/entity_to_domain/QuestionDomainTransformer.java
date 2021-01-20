package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.question.*;
import ua.edu.ratos.service.domain.ThemeDomain;
import ua.edu.ratos.service.domain.question.*;
import ua.edu.ratos.service.transformer.AnswerMapper;
import ua.edu.ratos.service.transformer.HelpMapper;
import ua.edu.ratos.service.transformer.ResourceMapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Deprecated
@Slf4j
@Component
@AllArgsConstructor
public class QuestionDomainTransformer {

    private final AnswerMapper answerMapper;

    private final HelpMapper helpMapper;

    private final ResourceMapper resourceMapper;

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
        entity.getAnswers().forEach(a-> domain.addAnswer(answerMapper.toDomain(a)));
        return domain;
    }

    public QuestionFBSQDomain toDomain(@NonNull final QuestionFBSQ entity) {
        QuestionFBSQDomain domain = new QuestionFBSQDomain();
        mapDomain(entity, domain);
        domain.setAnswer(answerMapper.toDomain(entity.getAnswer()));
        return domain;
    }

    public QuestionMCQDomain toDomain(@NonNull final QuestionMCQ entity) {
        QuestionMCQDomain domain = new QuestionMCQDomain();
        mapDomain(entity, domain);
        domain.setSingle(entity.isSingle());
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a-> domain.addAnswer(answerMapper.toDomain(a)));
        return domain;
    }

    public QuestionMQDomain toDomain(@NonNull final QuestionMQ entity) {
        QuestionMQDomain domain = new QuestionMQDomain();
        mapDomain(entity, domain);
        domain.setAnswers(new HashSet<>());
        entity.getAnswers().forEach(a -> domain.addAnswer(answerMapper.toDomain(a)));
        return domain;
    }

    public QuestionSQDomain toDomain(@NonNull final QuestionSQ entity) {
        QuestionSQDomain domain = new QuestionSQDomain();
        mapDomain(entity, domain);
        entity.getAnswers().forEach(a -> domain.add(answerMapper.toDomain(a)));
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
        domain.setType(entity.getType().getTypeId());

        domain.setHelpDomain((entity.getHelp().isPresent())
                ? helpMapper.toDomain(entity.getHelp().get()) : null);
        if (entity.getResources()!=null) {
            domain.setResourceDomains(entity.getResources()
                    .stream()
                    .map(resourceMapper::toDomain)
                    .collect(Collectors.toSet()));
        } else {
            domain.setResourceDomains(Collections.emptySet());
        }
        domain.setPartialResponseAllowed(entity.isPartialResponseAllowed());
    }
}
