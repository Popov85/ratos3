package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"faculty"})
@Entity
@Table(name = "class")
@NoArgsConstructor
public class Clazz {

    public Clazz(Long classId, String name) {
        this.classId = classId;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fac_id")
    private Faculty faculty;
}
