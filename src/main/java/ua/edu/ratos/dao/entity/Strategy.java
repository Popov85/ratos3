package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Pre-defined strategies:
 * ThemeDomain -> Type -> Level -> QuestionDomain
 * 1) Default: randomised irrespective to type and level, but observe themes order
 * 2) Full randomized: randomized irrespective to themeDomain order, type or level
 * 3) Types then levels.
 * 4) To be continued...
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "strategy")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
