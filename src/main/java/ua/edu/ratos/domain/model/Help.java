package ua.edu.ratos.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name = "help")
public class Help {
    @Id
    @GeneratedValue
    private Long helpId;

    @Column(name="text")
    private String help;

    @ManyToOne
    @JoinColumn(name = "help_resource_id", foreignKey = @ForeignKey(name = "fk_help_resource_resource_id"))
    private Resource resource;
}
