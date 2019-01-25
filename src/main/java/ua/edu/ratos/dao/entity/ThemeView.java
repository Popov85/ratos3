package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@Entity
@Table(name="theme_type_view")
public class ThemeView {

    @EmbeddedId
    private ThemeViewId themeViewId = new ThemeViewId();

    @Column(name = "dep_id")
    private Long depId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name="theme")
    private String theme;

    @Column(name="type")
    private String type;

    @Column(name="L1")
    private short l1;

    @Column(name="L2")
    private short l2;

    @Column(name="L3")
    private short l3;

    /**
     * Sum: l1 + l2 + l3,
     * Total quantity of questions of this type for selection
     */
    @Column(name="total")
    private int questions;
}
