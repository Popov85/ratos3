package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"scheme", "typeLevelSettings"})
@Entity
@Table(name="scheme_theme")
public class SchemeTheme {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="scheme_theme_id")
    private Long schemeThemeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name="theme_order")
    private short order;

    @OneToMany(mappedBy = "schemeTheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchemeThemeTypeLevelSettings> typeLevelSettings = new ArrayList<>();

    public void addTypeLevelSettings(SchemeThemeTypeLevelSettings typeLevelSettings) {
        this.typeLevelSettings.add(typeLevelSettings);
        typeLevelSettings.setSchemeTheme(this);
    }

    public void removeTypeLevelSettings(SchemeThemeTypeLevelSettings typeLevelSettings) {
        this.typeLevelSettings.remove(typeLevelSettings);
        typeLevelSettings.setSchemeTheme(null);
    }

}
