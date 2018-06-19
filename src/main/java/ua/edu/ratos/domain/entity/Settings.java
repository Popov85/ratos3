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
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "set_id")
    private Long setId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "seconds_per_question")
    private int secondsPerQuestion;

    @Column(name = "questions_per_sheet")
    private short questionsPerSheet;

    @Column(name = "days_keep_result_details")
    private short daysKeepResultDetails;

    @Column(name = "threshold_3")
    private byte threshold3;

    @Column(name = "threshold_4")
    private byte threshold4;

    @Column(name = "threshold_5")
    private byte threshold5;

    @Column(name = "level_2_coefficient")
    private float level2Coefficient;

    @Column(name = "level_3_coefficient")
    private float level3Coefficient;

    @Column(name = "display_percent")
    private boolean displayPercent;

    @Column(name = "display_mark")
    private boolean displayMark;

}
