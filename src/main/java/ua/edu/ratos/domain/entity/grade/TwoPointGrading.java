package ua.edu.ratos.domain.entity.grade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.service.session.grade.GradedResult;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "two_point")
@Cacheable
public class TwoPointGrading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "two_id")
    private Long twoId;

    @Column(name = "name")
    private String name;

    @Column(name = "threshold")
    private byte threshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grading_id")
    private Grading grading;

    @Column(name="is_default")
    private boolean isDefault;

    @Column(name = "is_deleted")
    private boolean deleted;


    public GradedResult grade(final double percent) {
        byte grade;
        if (percent<threshold) {
            grade= 0;
        } else {
            grade = 1;
        }
        return new GradedResult(grade>0, grade);
    }
}
