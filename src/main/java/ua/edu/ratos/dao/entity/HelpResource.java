package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "help_resource")
@Cacheable
public class HelpResource {

    @Id
    @Column(name = "help_id")
    private Long helpId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "help_id")
    private Help help;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;
}
