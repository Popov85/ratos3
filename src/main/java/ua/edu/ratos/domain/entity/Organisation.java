package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "deleted")
@Entity
@Table(name = "organisation")
//@SQLDelete(sql = "UPDATE organisation SET is_deleted = 1 WHERE org_id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_deleted")
    private boolean deleted;
}
