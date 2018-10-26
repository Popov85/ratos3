package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.domain.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.service.dto.response.ResponseMultipleChoice;
import ua.edu.ratos.service.dto.session.QuestionMCQOutDto;
import ua.edu.ratos.service.dto.session.QuestionOutDto;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Cacheable
@DiscriminatorValue(value = "1")
@DynamicUpdate
public class QuestionMultipleChoice extends Question {
    /**
     * Is it a question with the single correct answer?
     */
    @Transient
    private boolean isSingle;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnswerMultipleChoice> answers = new ArrayList<>();

    public void addAnswer(AnswerMultipleChoice answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerMultipleChoice answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
    }

    /**
     * Creates a new empty Question object (for parsers)
     * @return newly created empty Question
     */
    public static QuestionMultipleChoice createEmpty() {
        QuestionMultipleChoice q = new QuestionMultipleChoice();
        q.setQuestion("");
        q.setLevel((byte)1);
        q.setAnswers(new ArrayList<>());
        return q;
    }
    /**
     * Checks whether this question contains only one correct answerIds or multiple correct answers
     * @return
     */
    public boolean isSingle() {
        if (!isValid()) throw new RuntimeException("Invalid question");
        int counter = 0;
        for (AnswerMultipleChoice answer : answers) {
            if (!answer.isValid()) throw new RuntimeException("Invalid answerIds");
            if (answer.getPercent()!=0) counter++;
        }
       return (counter > 1) ? false : true;
    }

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answers == null) return false;
        if (this.answers.isEmpty()) return false;
        if (this.answers.size()<2) return false;
        if (!isValid(this.answers)) return false;
        return true;
    }

    private boolean isValid(@NonNull List<AnswerMultipleChoice> list) {
        int sum = 0;
        for (AnswerMultipleChoice a : list) {
            if (a == null) return false;
            if (!a.isValid()) return false;
            if (a.getPercent()==0 && a.isRequired()) a.setRequired(false);
            sum+=a.getPercent();
        }
        if (sum!=100) return false;
        return true;
    }

    public int evaluate(ResponseMultipleChoice response) {
        List<Long> zeroAnswers = getZeroAnswers();
        Set<Long> ids = response.getAnswerIds();
        for (Long id: ids) {
            if (zeroAnswers.contains(id)) return 0;
        }
        List<Long> requiredAnswers = getRequiredAnswers();
        for (Long requiredAnswer : requiredAnswers) {
            if(!this.answers.contains(requiredAnswer)) return 0;
        }
        int result = 0;
        for (Long id: ids) {
            for (AnswerMultipleChoice answer : this.answers) {
                if (id==(answer.getAnswerId()))
                    result+=answer.getPercent();
            }
        }
        return result;
    }

    private List<Long> getZeroAnswers() {
        List<Long> zeroAnswers = this.answers
                .stream()
                .filter(answer -> answer.getPercent() == 0)
                .map(AnswerMultipleChoice::getAnswerId)
                .collect(Collectors.toList());
        return zeroAnswers;
    }

    private List<Long> getRequiredAnswers() {
        List<Long> requiredAnswers = new ArrayList<>();
        List<AnswerMultipleChoice> answers = this.answers;
        requiredAnswers.addAll(answers
                .stream()
                .filter(AnswerMultipleChoice::isRequired)
                .map(AnswerMultipleChoice::getAnswerId)
                .collect(Collectors.toList()));
        return requiredAnswers;
    }

    @Override
    public QuestionMCQOutDto toDto(boolean mixable) {
        ModelMapper modelMapper = new ModelMapper();
        final QuestionOutDto questionOutDto = super.toDto(mixable);
        QuestionMCQOutDto dto = modelMapper
                .map(questionOutDto, QuestionMCQOutDto.class)
                .setSingle(isSingle());
        this.answers.forEach(a-> dto.add(a.toDto()));
        if (mixable) {
            dto.setAnswers(collectionShuffler
                .shuffle(dto.getAnswers())
                .stream()
                .collect(Collectors.toSet()));
        }
        return dto;
    }

    @Override
    public String toString() {
        return "QuestionMultipleChoice{" +
                "isSingle=" + isSingle +
                ", questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", deleted=" + deleted +
                '}';
    }
}
