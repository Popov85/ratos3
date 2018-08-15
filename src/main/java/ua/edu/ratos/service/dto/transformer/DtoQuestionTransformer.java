package ua.edu.ratos.service.dto.transformer;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.answer.*;
import ua.edu.ratos.domain.entity.question.*;
import ua.edu.ratos.service.dto.entity.*;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class DtoQuestionTransformer {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionMultipleChoice fromDto(@NonNull QuestionMCQInDto dto) {
        check(dto.getAnswers(), 2, "This question must contain at least 2 answers");
        QuestionMultipleChoice question = new QuestionMultipleChoice();
        mapDto(dto, question);
        dto.getAnswers().forEach(a-> {
            final AnswerMultipleChoice ans = modelMapper.map(a, AnswerMultipleChoice.class);
            if (a.getResourceId()!=0) ans.addResource(em.find(Resource.class, a.getResourceId()));
            question.addAnswer(ans);
        });
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionFillBlankSingle fromDto(@NonNull QuestionFBSQInDto dto) {
        check(dto.getAnswer().getPhrasesIds(), 1, "This question must contain at least one accepted phrase");
        QuestionFillBlankSingle question = new QuestionFillBlankSingle();
        mapDto(dto, question);
        AnswerFBSQInDto answerDto = dto.getAnswer();
        final AnswerFillBlankSingle answer = modelMapper.map(answerDto, AnswerFillBlankSingle.class);
        Set<AcceptedPhrase> acceptedPhrases = new HashSet<>();
        dto.getAnswer().getPhrasesIds().forEach(p-> {
            acceptedPhrases.add(em.find(AcceptedPhrase.class, p));
        });
        answer.setSettings(em.getReference(SettingsAnswerFillBlank.class, dto.getAnswer().getSetId()));
        answer.setAcceptedPhrases(acceptedPhrases);
        question.addAnswer(answer);
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionFillBlankMultiple fromDto(@NonNull QuestionFBMQInDto dto) {
        check(dto.getAnswers(), 1, "This question must contain at least 1 answer");
        QuestionFillBlankMultiple question = new QuestionFillBlankMultiple();
        mapDto(dto, question);
        dto.getAnswers().forEach(a->{
            final AnswerFillBlankMultiple answer = modelMapper.map(a, AnswerFillBlankMultiple.class);
            answer.setSettings(em.getReference(SettingsAnswerFillBlank.class, a.getSetId()));
            Set<AcceptedPhrase> acceptedPhrases = new HashSet<>();
            a.getPhrasesIds().forEach(p->{
                acceptedPhrases.add(em.find(AcceptedPhrase.class, p));
            });
            answer.setAcceptedPhrases(acceptedPhrases);
            question.addAnswer(answer);
        });
        return question;
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionMatcher fromDto(QuestionMQInDto dto) {
        check(dto.getAnswers(), 2, "This question must contain at least 2 answers");
        QuestionMatcher question = new QuestionMatcher();
        mapDto(dto, question);
        dto.getAnswers().forEach(a->{
            final AnswerMatcher answer = modelMapper.map(a, AnswerMatcher.class);
            if (a.getRightPhraseResourceId()!=0) answer.addResource(em.find(Resource.class, a.getRightPhraseResourceId()));
            question.addAnswer(answer);
        });
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public QuestionSequence fromDto(QuestionSQInDto dto) {
        check(dto.getAnswers(), 3, "This question must contain at least 3 answers");
        QuestionSequence question = new QuestionSequence();
        mapDto(dto, question);
        dto.getAnswers().forEach(a->{
            final AnswerSequence answer = modelMapper.map(a, AnswerSequence.class);
            if (a.getResourceId()!=0) answer.addResource(em.find(Resource.class, a.getResourceId()));
            question.addAnswer(answer);
        });
        return question;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mapDto(@NonNull QuestionInDto dto, @NonNull Question question) {
        question.setQuestionId(dto.getQuestionId());
        question.setQuestion(dto.getQuestion());
        question.setLevel(dto.getLevel());
        question.setTheme(em.getReference(Theme.class, dto.getThemeId()));
        question.setType(em.getReference(QuestionType.class, dto.getQuestionTypeId()));
        question.setLang(em.getReference(Language.class, dto.getLangId()));
        if (dto.getHelpId()!=0) {
            Set<Help> helps = new HashSet<>();
            Help help = em.find(Help.class, dto.getHelpId());
            helps.add(help);
            question.setHelp(helps);
        } else {
            question.getHelp().get().clear();
        }
        if (dto.getResourcesIds()!=null) {
            Set<Resource> resources = new HashSet<>();
            dto.getResourcesIds().forEach(id->resources.add(em.find(Resource.class, id)));
            question.setResources(resources);
        } else {
            question.getResources().get().clear();
        }
    }

    private <T> void check(Collection<T> collection, int threshold, String message) {
        if (collection==null || collection.isEmpty() || collection.size()<threshold)
            throw new IllegalStateException(message);
    }

}
