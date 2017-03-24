package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Andrey on 23.03.2017.
 */
@Data
@Getter
@Entity
@Table(name = "quest")
public class Question {
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @Column(name = "theme")
        private Long theme;

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
}
