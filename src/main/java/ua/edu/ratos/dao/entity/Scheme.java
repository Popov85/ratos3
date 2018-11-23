package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.dao.entity.grade.Grading;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"strategy", "settings", "mode", "grading", "course", "staff", "schemeThemes"})
@Entity
@Table(name="scheme")
@Cacheable
@Where(clause = "is_deleted = 0")
public class Scheme {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="scheme_id")
    private Long schemeId;

    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_id")
    private Settings settings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_id")
    private Mode mode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grading_id")
    private Grading grading;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Staff staff;

    @Column(name="created")
    private LocalDateTime created;

    @Column(name="is_active")
    private boolean active;

    @Column(name="is_deleted")
    private boolean deleted;

    /**
     * Scheme becomes completed as soon as at least one Theme is associated with it and all other settings are set;
     * if the last Theme associated with a Scheme gets deleted, the Scheme becomes incomplete
     */
    @Column(name="is_completed")
    private boolean completed;

    @OrderColumn(name = "theme_order")
    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<SchemeTheme> schemeThemes = new ArrayList<>();

    public void addSchemeTheme(@NonNull SchemeTheme schemeTheme) {
        this.schemeThemes.add(schemeTheme);
        schemeTheme.setScheme(this);
    }

    public void removeSchemeTheme(@NonNull SchemeTheme schemeTheme) {
        this.schemeThemes.remove(schemeTheme);
        schemeTheme.setScheme(null);
    }

    public void clearSchemeTheme() {
        this.schemeThemes.clear();
    }

    public ua.edu.ratos.service.session.domain.Scheme toDomain() {
        return new ua.edu.ratos.service.session.domain.Scheme()
                .setSchemeId(schemeId)
                .setName(name)
                .setStrategy(strategy.toDomain())
                .setSettings(settings.toDomain())
                .setMode(mode.toDomain())
                .setGrading(grading.toDomain());
    }

}
