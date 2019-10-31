package ua.edu.ratos.dao.entity.grading;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "fourPointGrading")
@Entity
@Table(name = "scheme_four_point")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchemeFourPoint {
    @Id
    @Column(name = "scheme_id")
    private Long schemeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "four_point_id")
    private FourPointGrading fourPointGrading;
}
