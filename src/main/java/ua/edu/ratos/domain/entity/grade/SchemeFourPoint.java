package ua.edu.ratos.domain.entity.grade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.domain.entity.Staff;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scheme_four_point")
@Cacheable
public class SchemeFourPoint {

    @Id
    @Column(name = "scheme_id")
    private Long schemeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "four_point_id")
    private FourPointGrading fourPointGrading;

}
