package ua.edu.ratos.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.ResultDetails;
import ua.edu.ratos.domain.entity.ResultTheme;

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

    @Column(name = "percent")
    private float percent;

    @Column(name = "mark")
    private byte mark;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "session_begin")
    private LocalDateTime sessionBegin;

    @Column(name = "session_eng")
    private LocalDateTime sessionEnd;

    @Column(name = "is_timeouted")
    private boolean timeouted;

    @OneToMany(mappedBy = "result", cascade = {CascadeType.PERSIST})
    private List<ResultTheme> resultTheme;

    @OneToOne(mappedBy = "result", cascade = CascadeType.PERSIST)
    private ResultDetails resultDetails;


}
