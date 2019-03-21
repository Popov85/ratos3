package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    @MapsId
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "detail_id")
    private Result result;

    @Column(name = "data")
    private String jsonData;

    @Column(name = "when_remove")
    private LocalDateTime whenRemove;

}
