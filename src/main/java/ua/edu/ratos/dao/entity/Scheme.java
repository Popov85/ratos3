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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {"strategy", "settings", "mode", "grading", "course", "staff", "themes", "groups"})
@Entity
@Table(name="scheme")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Column(name="lms_only")
    private boolean lmsOnly;

    @Column(name="is_deleted")
    private boolean deleted;

    /**
     * SchemeDomain becomes completed as soon as at least one ThemeDomain is associated with it and all other settingsDomain are set;
     * if the last ThemeDomain associated with a SchemeDomain gets deleted, the SchemeDomain becomes incomplete
     */
    @Column(name="is_completed")
    private boolean completed;

    @OrderColumn(name = "theme_order")
    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<SchemeTheme> themes = new ArrayList<>();

    public void addSchemeTheme(@NonNull SchemeTheme schemeTheme) {
        this.themes.add(schemeTheme);
        schemeTheme.setScheme(this);
    }

    public void removeSchemeTheme(@NonNull SchemeTheme schemeTheme) {
        this.themes.remove(schemeTheme);
        schemeTheme.setScheme(null);
    }

    public void clearSchemeTheme() {
        this.themes.clear();
    }

    @OneToMany(mappedBy = "scheme", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GroupScheme> groups = new HashSet<>();

    public void addGroup(Group group) {
        GroupScheme groupScheme = new GroupScheme();
        groupScheme.setGroup(group);
        groupScheme.setScheme(this);
        groupScheme.setEnabled(true);
        this.groups.add(groupScheme);
    }
}
