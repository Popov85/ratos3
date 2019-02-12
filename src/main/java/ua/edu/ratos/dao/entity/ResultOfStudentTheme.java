package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"result", "theme"})
@Entity
@Table(name = "result_theme")
public class ResultOfStudentTheme {

    @EmbeddedId
    private ResultThemeId resultThemeId = new ResultThemeId();

    @MapsId("resultId")
    @ManyToOne()
    @JoinColumn(name = "result_id")
    private ResultOfStudent result;

    @MapsId("themeId")
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name = "percent")
    private double percent;
}
