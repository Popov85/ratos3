package ua.zp.zsmu.ratos.learning_session.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 23.03.2017.
 */
@Data
@Entity
@Getter
@Setter
@ToString(exclude={"answers","resources"})
@Table(name = "quest")
public class Question implements Serializable {

        private static final long serialVersionUID = -5996578036570248337L;

        public Question() {
                this.title = "";
                this.level = 1;
                this.answers = new ArrayList<>();
                this.help = "";
        }

        public Question(final String question, final int difficulty, final List<Answer> answers, final String help) {
                this.title = question;
                this.level = (short) difficulty;
                this.answers = answers;
                this.help = help;
        }


        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "theme")
        private Theme theme;

        @Column(name = "concept")
        private String concept;

        @Column(name = "helpstr")
        private String helpString;

        @Column(name = "helpsrc")
        private String helpSource;

        @Column(name = "level")
        private short level;

        @Column(name = "help_title")
        private String helpTitle;

        @Column(name = "help")
        private String help;

        @OneToMany(fetch = FetchType.EAGER)
        @JoinColumn(name = "qid")
        private List<Answer> answers = new ArrayList<>();

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @OneToMany
        @JoinColumn(name = "qid")
        private List<Resource> resources = new ArrayList<>();


        public String getQuestion() {
                return title;
        }

        public void setDifficulty(int difficulty) {
                this.level = (short) difficulty;
        }

        public void setQuestion(String question) {
                this.title = question;
        }
}
