package ua.edu.ratos.service.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long themeId;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemeDomain that = (ThemeDomain) o;
        return Objects.equals(themeId, that.themeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeId);
    }
}
