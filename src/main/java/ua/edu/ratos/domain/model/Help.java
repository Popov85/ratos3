package ua.edu.ratos.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.question.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"question", "resources"})
@Entity
@Table(name = "help")
public class Help {
    @Id
    @Column(name = "help_id")
    private Long helpId;

    @Column(name = "text")
    private String help;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "help_id")
    private Question question;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "help_resource",
            joinColumns = @JoinColumn(name = "help_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private List<Resource> resources;
}
