package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.answer.*;
import ua.edu.ratos.service.domain.answer.*;
import ua.edu.ratos.service.transformer.ResourceMapper;

import java.util.stream.Collectors;

@Slf4j
@Component
public class AnswerDomainTransformer {

    private ResourceMapper resourceMapper;

    private SettingsFBDomainTransformer settingsFBDomainTransformer;

    private PhraseDomainTransformer phraseDomainTransformer;

    @Autowired
    public void setResourceDomainTransformer(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @Autowired
    public void setSettingsFBDomainTransformer(SettingsFBDomainTransformer settingsFBDomainTransformer) {
        this.settingsFBDomainTransformer = settingsFBDomainTransformer;
    }

    @Autowired
    public void setPhraseDomainTransformer(PhraseDomainTransformer phraseDomainTransformer) {
        this.phraseDomainTransformer = phraseDomainTransformer;
    }

    public AnswerFBMQDomain toDomain(@NonNull final AnswerFBMQ entity) {
        return new AnswerFBMQDomain()
                .setAnswerId(entity.getAnswerId())
                .setPhrase(entity.getPhrase())
                .setOccurrence(entity.getOccurrence())
                .setSettings(settingsFBDomainTransformer.toDomain(entity.getSettings()))
                .setAcceptedPhraseDomains(entity.getAcceptedPhrases()
                        .stream()
                        .map(phraseDomainTransformer::toDomain)
                        .collect(Collectors.toSet()));
    }

    public AnswerFBSQDomain toDomain(@NonNull final AnswerFBSQ entity) {
        return new AnswerFBSQDomain()
                .setAnswerId(entity.getAnswerId())
                .setSettings(settingsFBDomainTransformer.toDomain(entity.getSettings()))
                .setAcceptedPhraseDomains(entity.getAcceptedPhrases()
                        .stream()
                        .map(phraseDomainTransformer::toDomain)
                        .collect(Collectors.toSet()));
    }

    public AnswerMCQDomain toDomain(@NonNull final AnswerMCQ entity) {
        return new AnswerMCQDomain()
                .setAnswerId(entity.getAnswerId())
                .setAnswer(entity.getAnswer())
                .setPercent(entity.getPercent())
                .setRequired(entity.isRequired())
                .setResourceDomain((entity.getResource().isPresent()) ?
                        resourceMapper.toDomain(entity.getResource().get()) : null);
    }

    public AnswerMQDomain toDomain(@NonNull final AnswerMQ entity) {
        return new AnswerMQDomain()
                .setAnswerId(entity.getAnswerId())
                .setLeftPhraseDomain(phraseDomainTransformer.toDomain(entity.getLeftPhrase()))
                .setRightPhraseDomain(phraseDomainTransformer.toDomain(entity.getRightPhrase()));
    }

    public AnswerSQDomain toDomain(@NonNull final AnswerSQ entity) {
        return new AnswerSQDomain()
                .setAnswerId(entity.getAnswerId())
                .setPhraseDomain(phraseDomainTransformer.toDomain(entity.getPhrase()))
                .setOrder(entity.getOrder());
    }
}
