package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class GroupSchemeId implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "scheme_id")
    private Long schemeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupSchemeId that = (GroupSchemeId) o;
        return Objects.equals(schemeId, that.schemeId) &&
                Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemeId, groupId);
    }
}
