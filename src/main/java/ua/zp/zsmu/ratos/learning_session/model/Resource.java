package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Andrey on 4/16/2017.
 */
@Data
@Entity
@Table(name="controls")
public class Resource {
        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "data")
        private byte[] data;

        @Column(name = "src")
        private String src;

        @Column(name = "alt")
        private String alt;

        // TQuest (Display method) explains:
        // 0 - MediaPlayer (0)
        // 1 - img (767, blob is present)
        // 2 - iFrame (0)
        // 3 - Question (2128)
        // 4 - Answers section(2155)
        // 5 - Next button (2190)
        // 6 - Help button(7)
        // 7 - Hint button(1792)
        // 8 - (0)
        // 9 - Alt message (248)
        // 10 - Skip button (245)
        // 11 - Flash Player(11)
        @Column(name = "ct")
        private String type;

        @Column(name = "l")
        private short leftPosition;

        @Column(name = "t")
        private short topPosition;

        @Column(name = "h")
        private short height;

        @Column(name = "w")
        private short width;

        @Column(name = "rule")
        private boolean isRule;

        // 0 or 100 on null
        @Column(name = "repl_pr")
        private int percentage;

        @Column(name = "fontface")
        private String fontFace;

        @Column(name = "fontcolor")
        private int fontColor;

        @Column(name = "fontheight")
        private int fontHeight;

        @Column(name = "fontstyle")
        private int fontStyle;

        @Column(name = "fontstylebold")
        private boolean isFontStyleBold;

        @Column(name = "fontstyleitalic")
        private boolean isFontStyleItalic;

        // True
        @Column(name = "req")
        private boolean isRequired;
}
