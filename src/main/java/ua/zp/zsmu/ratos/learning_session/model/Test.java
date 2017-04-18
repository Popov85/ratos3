package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Andrey on 14.04.2017.
 */
@Data
@Entity
@Table(name="test")
public class Test {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;
}
