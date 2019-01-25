package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@Embeddable
public class ThemeViewId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "theme_id")
    private Long themeId;

    @Column(name = "type_id")
    private Long typeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemeViewId that = (ThemeViewId) o;
        return Objects.equals(themeId, that.themeId) &&
                Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(themeId, typeId);
    }
}
