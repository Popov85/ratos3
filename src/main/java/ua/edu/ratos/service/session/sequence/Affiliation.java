package ua.edu.ratos.service.session.sequence;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Affiliation {

    private Long themeId;

    private Long typeId;

    private byte level;

    /**
     * According to the scheme's settings,
     * requested quantity of questions of this theme, type and level.
     */
    private short quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Affiliation that = (Affiliation) o;
        return level == that.level &&
                Objects.equals(themeId, that.themeId) &&
                Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeId, typeId, level);
    }
}
