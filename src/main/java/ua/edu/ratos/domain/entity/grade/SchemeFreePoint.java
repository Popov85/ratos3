package ua.edu.ratos.domain.entity.grade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scheme_free_point")
@Cacheable
public class SchemeFreePoint {

    @Id
    @Column(name = "scheme_id")
    private Long schemeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_point_id")
    private FreePointGrading freePointGrading;
}
