package ua.edu.ratos.domain.model.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.model.Resource;
import javax.persistence.*;

@Setter
@Getter
@ToString
//@Entity
@Table(name="answer_mcq")
public class AnswerMultipleChoice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long answerId;

    @Column(name="answer")
    private String answer;

    @Column(name="percent")
    private short percent;

    @Column(name="is_required")
    private boolean isRequired;

    @ManyToOne
    @JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "fk_answer_mcq_resource_resource_id"))
    private Resource resource;

    public boolean isValid() {
        if (answer == null) return false;
        if (answer.isEmpty())return false;
        if (percent < 0 || percent > 100) return false;
        if (percent==0 && isRequired) isRequired = false;
        return true;
    }
}
