package ua.edu.ratos.domain.entity;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name="theme_type")
@Immutable
public class ThemeView {

    @EmbeddedId
    private ThemeViewId themeViewId = new ThemeViewId();

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
    private int total;
}
