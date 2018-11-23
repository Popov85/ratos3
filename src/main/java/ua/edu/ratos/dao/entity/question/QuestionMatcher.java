package ua.edu.ratos.dao.entity.question;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.dao.entity.answer.AnswerMatcher;
import ua.edu.ratos.service.session.domain.question.QuestionMQ;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Match.
 * Matching questions consist of two columns, typically one column is on the left and one column is on the right.
 * We will refer to the left side as 'Clues' and the right side as 'Matches'.
 * The objective is to pair the clues on the left side with their matches on the right.
 * These can be created with using helpAvailable on both sides or a mix of helpAvailable with media,
 * such as images, audio or video.
 *
 * @see <a href="https://www.classmarker.com/learn/question-types/matching-questions/">Match</a>
 * @author Andrey P.
 */

@Slf4j
@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "4")
@DynamicUpdate
public class QuestionMatcher extends Question {

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnswerMatcher> answers = new ArrayList<>();

    public void addAnswer(AnswerMatcher answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    @Override
    public QuestionMQ toDomain() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionMQ questionMQ = modelMapper.map(this, QuestionMQ.class);
        questionMQ.setLang(this.lang.getAbbreviation());
        questionMQ.setType(this.type.getTypeId());
        questionMQ.setHelp((super.helpToDomain().isPresent()) ? super.helpToDomain().get(): null);
        questionMQ.setResources(super.resourcesToDomain());
        this.answers.forEach(a -> questionMQ.addAnswer(a.toDomain()));
        return questionMQ;
    }

}
