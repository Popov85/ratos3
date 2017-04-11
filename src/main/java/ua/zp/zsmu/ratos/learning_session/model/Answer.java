package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andrey on 31.03.2017.
 */
@Data
@Getter
@Setter
@Entity
@Table(name = "answer")
public class Answer implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @Column(name = "required")
        private boolean isRequired;

        @Column(name="pr")
        private short percentage;

        @Column(name="context_hint")
        private String contextHint;

}
