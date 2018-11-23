package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Accessors(chain = true)
@Embeddable
public class ThemeViewId implements Serializable {

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "fac_id")
    private Long facId;

    @Column(name = "dep_id")
    private Long depId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "theme_id")
    private Long themeId;

    @Column(name = "type_id")
    private Long typeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemeViewId that = (ThemeViewId) o;
        return Objects.equals(orgId, that.orgId) &&
                Objects.equals(facId, that.facId) &&
                Objects.equals(depId, that.depId) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(themeId, that.themeId) &&
                Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orgId, facId, depId, courseId, themeId, typeId);
    }
}
