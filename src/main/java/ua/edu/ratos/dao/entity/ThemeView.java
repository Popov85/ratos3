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

    @Column(name="organisation")
    private String organisation;

    @Column(name="faculty")
    private String faculty;

    @Column(name="department")
    private String department;

    @Column(name="course")
    private String course;

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

    @Column(name="total")
    private int questions;
}
