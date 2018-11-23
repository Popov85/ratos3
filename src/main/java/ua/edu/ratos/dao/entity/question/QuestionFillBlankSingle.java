package ua.edu.ratos.dao.entity.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.dao.entity.SettingsAnswerFillBlank;
import ua.edu.ratos.dao.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.session.domain.question.QuestionFBSQ;
import ua.edu.ratos.service.session.domain.response.ResponseFillBlankSingle;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "2")
@DynamicUpdate
@NoArgsConstructor
public class QuestionFillBlankSingle extends Question {

    @OneToOne(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    private AnswerFillBlankSingle answer;

    public void addAnswer(AnswerFillBlankSingle answer) {
        this.answer = answer;
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerFillBlankSingle answer) {
        this.answer = null;
        answer.setQuestion(null);
    }

    @Override
    public QuestionFBSQ toDomain() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionFBSQ dto = modelMapper.map(this, QuestionFBSQ.class);
        dto.setLang(this.lang.getAbbreviation());
        dto.setType(this.type.getTypeId());
        dto.setHelp((super.helpToDomain().isPresent()) ? super.helpToDomain().get(): null);
        dto.setResources(super.resourcesToDomain());
        dto.setAnswer(this.answer.toDomain());
        return dto;
    }
}
