package ua.zp.zsmu.ratos.learning_session.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Andrey on 24.03.2017.
 */
@Data
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

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @OneToMany // LAZY by default
        @JoinColumn(name = "theme")
        private List<Question> questions;// = new ArrayList<>();

}
