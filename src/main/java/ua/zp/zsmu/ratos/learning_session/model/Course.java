package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Andrey on 31.03.2017.
 */
@Data
@Getter
@Entity
@Table(name = "course")
public class Course {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @OneToMany(fetch = FetchType.EAGER)
        @JoinColumn(name = "course")
        private Set<Theme> themes;
}
