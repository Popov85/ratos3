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
    @Column(name = "result_id")
    private Long detailId;

    @Column(name = "json_data")
    private String jsonData;

    @Column(name = "when_remove")
    private LocalDateTime whenRemove;

    @MapsId
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "result_id")
    private Result result;

}
