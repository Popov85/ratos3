package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.dao.entity.lms.LMSCourse;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString(exclude = {"staff", "department", "access"})
@Entity
@Table(name = "course")
@DynamicUpdate
@NoArgsConstructor
public class Course {

    public Course(Long courseId, String name) {
        this.courseId = courseId;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "name")
    private String name;

    @Column(name = "created", updatable = false)
    private OffsetDateTime created;

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Staff staff;

    // Course remains to belong to the department
    // even if staff changes department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "belongs_to")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_id")
    private Access access;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "course", orphanRemoval = true)
    private LMSCourse lmsCourse;
}
