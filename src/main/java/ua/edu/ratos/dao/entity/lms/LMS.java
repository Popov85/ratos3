package ua.edu.ratos.dao.entity.lms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.dao.entity.Organisation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"organisation", "credentials", "ltiVersion", "origins"})
@Entity
@Table(name = "lms")
@Where(clause = "is_deleted = 0")
@DynamicUpdate
public class LMS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "lms_id")
    private Long lmsId;

    @Column(name = "name")
    private String name;

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organisation organisation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id")
    private LTICredentials credentials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lti_version_id")
    private LTIVersion ltiVersion;

    @OneToMany(mappedBy = "lms", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<LMSOrigin> origins = new HashSet<>();

    public void addOrigin(LMSOrigin origin) {
        this.origins.add(origin);
        origin.setLms(this);
    }

    public void removeOrigin(LMSOrigin origin) {
        this.origins.remove(origin);
        origin.setLms(null);
    }
}
