package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "mode")
public class Mode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "mode_id")
    private Long modeId;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name="is_helpable")
    private boolean helpable;

    @Column(name="is_pyramid")
    private boolean pyramid;

    @Column(name="is_skipable")
    private boolean skipable;

    @Column(name="is_rightans")
    private boolean rightAnswer;

    @Column(name="is_resultdetails")
    private boolean resultDetails;

    @Column(name="is_pauseable")
    private boolean pauseable;

    @Column(name="is_preservable")
    private boolean preservable;

    @Column(name="is_reportable")
    private boolean reportable;

}
