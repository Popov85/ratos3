package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.Result;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString(exclude = "result")
@Entity
@Table(name = "result_details")
public class ResultDetails {
    @Id
    @Column(name = "detail_id")
    private Long detailId;

    @Column(name = "data")
    private String jsonData;

    @Column(name = "when_remove")
    private LocalDateTime whenRemove;

    @MapsId
    @OneToOne
    @JoinColumn(name = "result_id")
    private Result result;

}
