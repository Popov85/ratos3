package ua.edu.ratos.dao.entity.grade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "twoPointGrading")
@Entity
@Table(name = "scheme_two_point")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SchemeTwoPoint {
    @Id
    @Column(name = "scheme_id")
    private Long schemeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "two_point_id")
    private TwoPointGrading twoPointGrading;
}
