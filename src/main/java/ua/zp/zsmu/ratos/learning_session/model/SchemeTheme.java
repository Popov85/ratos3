package ua.zp.zsmu.ratos.learning_session.model;

/**
 * Created by Andrey on 07.04.2017.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@Getter
@ToString(exclude={"scheme", "theme"})
@Entity
@Table(name = "qids")
public class SchemeTheme {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @JsonIgnore
        //@JsonInclude(JsonInclude.Include.NON_NULL)
        @ManyToOne
        @JoinColumn(name = "scheme")
        private Scheme scheme;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @ManyToOne
        @JoinColumn(name = "qid")
        private Theme theme;

        @Column(name="n1")
        private short quantityOf1stLevelQuestions;

        @Column(name="n2")
        private short quantityOf2stLevelQuestions;

        @Column(name="n3")
        private short quantityOf3stLevelQuestions;
}
