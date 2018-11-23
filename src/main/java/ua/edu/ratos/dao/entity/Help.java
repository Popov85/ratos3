package ua.edu.ratos.dao.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Setter
@Getter
@ToString(exclude = {"staff", "helpResource"})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", updatable = false, nullable = false)
    private Staff staff;

    @OneToOne(mappedBy = "help", cascade = {CascadeType.ALL})
    private HelpResource helpResource;

    public Help(String name, String help) {
        this.name = name;
        this.help = help;
    }

    public ua.edu.ratos.service.session.domain.Help toDomain() {
        return new ua.edu.ratos.service.session.domain.Help()
                .setHelpId(helpId)
                .setName(this.name)
                .setHelp(this.help)
                .setResource((helpResource!=null) ? helpResource.getResource().toDomain(): null);
    }

    public Optional<HelpResource> getHelpResource() {
        return Optional.of(helpResource);
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
