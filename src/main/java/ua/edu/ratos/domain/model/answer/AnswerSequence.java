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
@Table(name = "answer_sq")
public class AnswerSequence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private long answerId;

    @Column(name="element")
    private String phrase;

    @Column(name="order")
    private short order;

    @ManyToOne
    @JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "fk_answer_sq_resource_resource_id"))
    private Resource resource;

    public boolean isValid() {
        if (this.phrase==null || this.phrase.isEmpty()) return false;
        if (order<0 || order>100) return false;
        return true;
    }
}
