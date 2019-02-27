package ua.edu.ratos.dao.entity.lms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString(exclude = {"lms"})
@Entity
@Table(name = "lms_origin")
public class LMSOrigin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "origin_id")
    private Long originId;

    @Column(name = "link")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lms_id")
    private LMS lms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LMSOrigin lmsOrigin = (LMSOrigin) o;
        return Objects.equals(link, lmsOrigin.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
