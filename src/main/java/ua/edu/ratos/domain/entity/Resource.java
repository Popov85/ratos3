package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@ToString(exclude = "staff")
@NoArgsConstructor
@Entity
@Table(name = "resource")
@Cacheable
public class Resource {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="resource_id")
    private Long resourceId;

    @Column(name="hyperlink")
    private String link;

    @Column(name="description")
    private String description;

    @Column(name="last_used", nullable = false)
    private LocalDateTime lastUsed = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    protected Staff staff;

    @Column(name="is_deleted")
    private boolean deleted;

    public Resource(String link, String description) {
        this.link = link;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(link, resource.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
