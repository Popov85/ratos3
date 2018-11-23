package ua.edu.ratos.dao.entity.grade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.session.grade.GradedResult;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "four_point")
@Cacheable
public class FourPointGrading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "four_id")
    private Long fourId;

    @Column(name = "name")
    private String name;

    @Column(name = "threshold_3")
    private byte threshold3;

    @Column(name = "threshold_4")
    private byte threshold4;

    @Column(name = "threshold_5")
    private byte threshold5;

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
        if (percent<=0) {
            grade = 2;
        } else if (percent>=threshold3 && percent< threshold4) {
            grade = 3;
        } else if (percent>=threshold4 && percent< threshold5) {
            grade = 4;
        } else {
            grade = 5;
        }
        return new GradedResult(grade>2, grade);
    }

}
