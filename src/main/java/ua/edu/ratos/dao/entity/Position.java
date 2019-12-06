package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "position")
@Where(clause = "is_deleted = 0")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "pos_id")
    private Long posId;

    @Column(name = "name")
    private String name;

    @Column(name="is_deleted")
    private boolean deleted;
}
