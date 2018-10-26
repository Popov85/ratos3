package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = "result")
@Entity
@Table(name = "result_theme")
public class ResultTheme {
    @EmbeddedId
    private ResultThemeId resultThemeId = new ResultThemeId();

    @MapsId("resultId")
    @ManyToOne
    @JoinColumn(name = "result_id")
    private Result result;

    @MapsId("themeId")
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name = "percent")
    private double percent;
}
