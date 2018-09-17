package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.service.dto.response.ResponseFillBlankMultiple;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "3")
@DynamicUpdate
public class QuestionFillBlankMultiple extends Question{

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnswerFillBlankMultiple> answers = new HashSet<>();

    public void addAnswer(AnswerFillBlankMultiple answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerFillBlankMultiple answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
    }

    public int evaluate(ResponseFillBlankMultiple response) {
        final List<ResponseFillBlankMultiple.Pair> pairs = response.enteredPhrases;
        for (ResponseFillBlankMultiple.Pair pair : pairs) {
            final Long phraseId = pair.phraseId;
            final String enteredPhrase = pair.enteredPhrase;
            Optional<AnswerFillBlankMultiple> answerFillBlankMultiple =
                    answers.stream().filter(a -> a.getAnswerId() == phraseId).findFirst();
            if (!answerFillBlankMultiple.isPresent()) throw new RuntimeException("Corrupt answerId");
            List<String> acceptedPhrases = answerFillBlankMultiple.get().getAcceptedPhrases()
                    .stream()
                    .map(p->p.getPhrase())
                    .collect(Collectors.toList());
            if (!acceptedPhrases.contains(enteredPhrase)) return 0;
        }
        return 100;
    }

    @Override
    public String toString() {
        return "QuestionFillBlankMultiple{" +
                ", questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", deleted=" + deleted +
                ", type=" + (type!=null ? type.getAbbreviation(): null) +
                ", lang=" + (lang!=null ? lang.getAbbreviation() : null) +
                '}';
    }
}
