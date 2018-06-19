package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.question.QuestionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = "schemeThemes")
@Entity
@Table(name="scheme")
public class Scheme {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="scheme_id")
    private Long schemeId;

    @Column(name="name")
    private String name;

    @Column(name="is_active")
    private boolean active;

    //@Setter(AccessLevel.NONE)
    @OrderColumn(name = "theme_order")
    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchemeTheme> schemeThemes = new ArrayList<>();

    public void addTheme(Theme theme, short order, QuestionType questionType, short level1, short level2, short level3) {
        SchemeTheme schemeTheme = new SchemeTheme();
        schemeTheme.setTheme(theme);
        schemeTheme.setScheme(this);
        schemeTheme.setOrder(order);

        SchemeThemeTypeLevelSettings typeLevelSettings = new SchemeThemeTypeLevelSettings();
        typeLevelSettings.setSchemeTheme(schemeTheme);
        typeLevelSettings.setType(questionType);
        typeLevelSettings.setLevel1(level1);
        typeLevelSettings.setLevel2(level2);
        typeLevelSettings.setLevel3(level3);

        schemeTheme.addTypeLevelSettings(typeLevelSettings);

        this.schemeThemes.add(schemeTheme);
    }


    public void removeTheme(Theme theme) {

    }

}
