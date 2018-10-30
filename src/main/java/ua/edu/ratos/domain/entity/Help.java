package ua.edu.ratos.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {"question", "staff", "resources"})
@NoArgsConstructor
@Entity
@Table(name = "help")
@Cacheable
public class Help {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "help_id", updatable = false, nullable = false)
    private Long helpId;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String help;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", updatable = false, nullable = false)
    private Staff staff;

    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "help_resource", joinColumns = @JoinColumn(name = "help_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources = new HashSet<>();

    public void addResource(Resource resource) {
        if (!this.resources.isEmpty()) throw
            new IllegalStateException("Currently, only one phraseResource can be associated with a help object");
        this.resources.add(resource);
    }

    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }

    public Help(String name, String help) {
        this.name = name;
        this.help = help;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Help help1 = (Help) o;
        return Objects.equals(help, help1.help);
    }

    @Override
    public int hashCode() {
        return Objects.hash(help);
    }
}
