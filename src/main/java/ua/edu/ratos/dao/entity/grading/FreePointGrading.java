package ua.edu.ratos.dao.entity.grading;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.dao.entity.Department;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.session.grade.GradedResult;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"staff", "department", "grading"})
@Entity
@Table(name = "free_point")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FreePointGrading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "free_id")
    private Long freeId;

    @Column(name = "name")
    private String name;

    @Column(name = "min_value")
    private short minValue;

    @Column(name = "pass_value")
    private short passValue;

    @Column(name = "max_value")
    private short maxValue;

    @Column(name="is_default")
    private boolean isDefault;

    @Column(name = "is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grading_id")
    private Grading grading;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "belongs_to")
    private Department department;

    /**
     * This grading system supports only integer values;
     * For fractional values, please, implement another Grading class
     * (with specified precision of rounding)
     * @param percent
     * @return
     */
    public GradedResult grade(final double percent) {
        short grade = (short) Math.round(minValue+(percent*(maxValue-minValue)/100));
        return new GradedResult(grade>=passValue, grade);
    }

}
