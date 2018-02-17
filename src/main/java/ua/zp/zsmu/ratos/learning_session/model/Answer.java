package ua.zp.zsmu.ratos.learning_session.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

        @JsonIgnore
        @Column(name="pr")
        private int percentage;

        // null
        @Column(name="context_hint")
        private String contextHint;

        public Answer(String answer, int percent, boolean isRequired) {
                this.title = answer;
                this.percentage = percent;
                this.isRequired = isRequired;
        }

        public Answer() {

        }

        public String getAnswer() {
                return title;
        }

        public int getCorrect() {
                return percentage;
        }

        public void setCorrect(Integer correct) {
                this.percentage = correct;
        }

        public void setAnswer(String answer) {
                this.title = answer;
        }
}
