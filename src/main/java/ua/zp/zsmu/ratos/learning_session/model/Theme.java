package ua.zp.zsmu.ratos.learning_session.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Andrey on 24.03.2017.
 */
@Data
@Getter
@Entity
@ToString(exclude="questions")
@Table(name = "theme")
public class Theme {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        //@JsonIgnore
        @OneToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "theme")
        private Set<Question> questions;

}
