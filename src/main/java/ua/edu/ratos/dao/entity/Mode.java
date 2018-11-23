package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"staff"})
@Entity
@Table(name = "mode")
@Cacheable
public class Mode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "mode_id")
    private Long modeId;

    @Column(name="name")
    private String name;

    @Column(name="is_helpable")
    private boolean helpable;

    @Column(name="is_pyramid")
    private boolean pyramid;

    @Column(name="is_skipable")
    private boolean skipable;

    /**
     * Whether or not to show right answers to the user after each batch?
     */
    @Column(name="is_rightans")
    private boolean rightAnswer;

    /**
     * Whether or not to disclose right answers after the end of the dto?
     */
    @Column(name="is_resultdetails")
    private boolean resultDetails;

    @Column(name="is_pauseable")
    private boolean pauseable;

    @Column(name="is_preservable")
    private boolean preservable;

    @Column(name="is_reportable")
    private boolean reportable;

    @Column(name="is_starrable")
    private boolean starrable;

    @Column(name="is_deleted")
    private boolean deleted;

    @Column(name="is_default")
    private boolean defaultMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    public ua.edu.ratos.service.session.domain.Mode toDomain() {
        return  new ModelMapper().map(this, ua.edu.ratos.service.session.domain.Mode.class);
    }

}
