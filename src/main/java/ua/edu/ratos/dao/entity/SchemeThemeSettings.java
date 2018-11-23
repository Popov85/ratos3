package ua.edu.ratos.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"schemeTheme", "type"})
@Entity
@Table(name="type_level")
@Cacheable
public class SchemeThemeSettings {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="type_level_id")
    private Long schemeThemeSettingsId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scheme_theme_id")
    private SchemeTheme schemeTheme;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id")
    private QuestionType type;

    @Column(name="level_1")
    private short level1;

    @Column(name="level_2")
    private short level2;

    @Column(name="level_3")
    private short level3;
}
