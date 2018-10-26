package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Pre-defined strategies:
 * Theme -> Type -> Level -> Question
 * 1) Default: randomised irrespective to type and level, but observe themes order
 * 2) Full randomized: randomized irrespective to theme order, type or level
 * 3) Types then levels.
 * 4) To be continued...
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "strategy")
@Cacheable
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "str_id")
    private Long strId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
