package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"strategy", "settings","mode", "course", "staff", "schemeThemes"})
@Entity
@Table(name="scheme")
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
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Staff staff;

    @Column(name="created")
    private LocalDateTime created;

    @Column(name="is_active")
    private boolean active = true;

    @Column(name="is_deleted")
    private boolean deleted;

    /**
     * Scheme becomes completed as soon as at least one Theme is associated with it and all other settings are set
     */
    @Column(name="is_completed")
    private boolean completed;

    @OrderColumn(name = "theme_order")
    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL, orphanRemoval = true)
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

}
