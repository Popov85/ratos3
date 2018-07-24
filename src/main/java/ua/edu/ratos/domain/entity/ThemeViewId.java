package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class ThemeViewId implements Serializable {

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
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(themeId, that.themeId) &&
                Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, themeId, typeId);
    }
}
