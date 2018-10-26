package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"resultTheme", "resultDetails"})
@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "res_id")
    private Long resId;

    @ManyToOne
    @JoinColumn(name = "scheme_id", updatable = false)
    protected Scheme scheme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    protected User user;

    @Column(name = "percent")
    private double percent;

    @Column(name = "grade")
    private Number grade;

    @Column(name = "is_passed")
    private boolean passed;

    @Column(name = "session_ended")
    private LocalDateTime sessionEnded;

    @Column(name = "session_lasted")
    private long sessionLasted;

    @Column(name = "is_timeouted")
    private boolean timeOuted;

    @OneToMany(mappedBy = "result", cascade = {CascadeType.PERSIST})
    private List<ResultTheme> resultTheme;

  /*  // Does not work out of the box
    // http://justonjava.blogspot.com/2010/09/lazy-one-to-one-and-one-to-many.html
    @OneToOne(mappedBy = "result", cascade = CascadeType.PERSIST, optional = false)
    private ResultDetails resultDetails;*/
}
