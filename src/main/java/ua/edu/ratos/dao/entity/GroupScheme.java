package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@ToString(exclude = {"group", "scheme"})
@Entity
@Table(name = "group_scheme")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupScheme {

    @EmbeddedId
    private GroupSchemeId groupSchemeId = new GroupSchemeId();

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @MapsId("schemeId")
    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupScheme that = (GroupScheme) o;
        return Objects.equals(groupSchemeId, that.groupSchemeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupSchemeId);
    }
}
