package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.dto.response.ResponseFillBlankSingle;
import ua.edu.ratos.service.dto.session.QuestionFBSQOutDto;
import ua.edu.ratos.service.dto.session.QuestionOutDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "2")
@DynamicUpdate
public class QuestionFillBlankSingle extends Question {

    @OneToOne(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    private AnswerFillBlankSingle answer;

    public QuestionFillBlankSingle() {
    }

    public QuestionFillBlankSingle(String question, byte level) {
        super(question, level);
    }

    public void addAnswer(AnswerFillBlankSingle answer) {
        this.answer = answer;
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerFillBlankSingle answer) {
        this.answer = null;
        answer.setQuestion(null);
    }

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answer == null || !this.answer.isValid()) return false;
        return true;
    }

    public int evaluate(ResponseFillBlankSingle response) {
        final String enteredPhrase = response.getEnteredPhrase();
        List<String> acceptedPhrases = answer.getAcceptedPhrases()
                .stream()
                .map(p->p.getPhrase())
                .collect(Collectors.toList());
        // TODO check for isTypoAllowed and isCaseSensitive
        if (acceptedPhrases.contains(enteredPhrase)) return 100;
        return 0;
    }

    @Override
    public QuestionFBSQOutDto toDto(boolean mixable) {
        ModelMapper modelMapper = new ModelMapper();
        final QuestionOutDto questionOutDto = super.toDto(mixable);
        QuestionFBSQOutDto dto = modelMapper
                .map(questionOutDto, QuestionFBSQOutDto.class);
        dto.setAnswer(this.answer.toDto());
        // Does not support any kind of shuffling
        return dto;
    }

    @Override
    public String toString() {
        return "QuestionFillBlankSingle {" +
                " questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", deleted=" + deleted +
                '}';
    }
}
