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
@Cacheable
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "set_id")
    private Long setId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    /**
     * Negative int is used for "not limited in time", default - do not limit
     */
    @Column(name = "seconds_per_question")
    private int secondsPerQuestion;

    /**
     * Specifies, whether or not to limit a single question in time, default - do not limit
     */
    @Column(name = "strict_seconds_per_question")
    private boolean strictControlTimePerQuestion;

    /**
     * Negative short is used for "all questions per sheet" option
     */
    @Column(name = "questions_per_sheet")
    private short questionsPerSheet;

    @Column(name = "days_keep_result_details")
    private short daysKeepResultDetails;

    @Column(name = "level_2_coefficient")
    private float level2Coefficient;

    @Column(name = "level_3_coefficient")
    private float level3Coefficient;

    @Column(name = "display_percent")
    private boolean displayPercent;

    @Column(name = "display_mark")
    private boolean displayMark;

    @Column(name = "display_theme_results")
    private boolean displayThemeResults;

    @Column(name="is_default")
    private boolean isDefault;

    @Column(name = "is_deleted")
    private boolean deleted;

}
