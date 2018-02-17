package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Andrey on 4/8/2017.
 */
@Data
@Getter
@Setter
@Entity
@Table(name="results")
public class Result {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name="tst_time")
        private Date testTime;

        @Column(name="name")
        private String studentName;

        @Column(name="surname")
        private String studentSurName;

        @Column(name="kurs")
        private String year;

        @Column(name="gr")
        private String group;

        @Column(name="faculty")
        private String faculty;

        @Column(name="ip")
        private String ip;

        @ManyToOne
        @JoinColumn(name="scheme")
        private Scheme scheme;

        @Column(name="percent")
        private int percent;

        @Column(name="mark")
        private int mark;
}
