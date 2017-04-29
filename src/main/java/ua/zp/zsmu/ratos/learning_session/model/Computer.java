package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Andrey on 28.04.2017.
 */
@Data
@Getter
@Setter
@Entity
@Table(name = "comp")
public class Computer {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "ip")
        private String ip;

        @Column(name = "comment")
        private String comment;
}
