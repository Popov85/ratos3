package ua.edu.ratos.domain.entity.grade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.entity.Scheme;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scheme_two_point")
@Cacheable
public class SchemeTwoPoint {

    @Id
    @Column(name = "scheme_id")
    private Long schemeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "two_point_id")
    private TwoPointGrading twoPointGrading;
}
