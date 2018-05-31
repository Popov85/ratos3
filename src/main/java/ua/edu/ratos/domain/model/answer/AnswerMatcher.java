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
@Table(name = "answer_mq")
public class AnswerMatcher {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private long answerId;

    @Column(name="left_phrase")
    private String leftPhrase;

    @Column(name="right_phrase")
    private String rightPhrase;

    @ManyToOne// Only applicable for right phrase
    @JoinColumn(name = "right_phrase_resource_id", foreignKey = @ForeignKey(name = "fk_answer_mq_resource_resource_id"))
    private Resource resource;

    public boolean isValid() {
        if (this.leftPhrase == null || this.rightPhrase==null) return false;
        if (this.leftPhrase.isEmpty()) return false;
        if (this.rightPhrase.isEmpty()) return false;
        return true;
    }
}
