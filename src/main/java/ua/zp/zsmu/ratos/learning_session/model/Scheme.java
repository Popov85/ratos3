package ua.zp.zsmu.ratos.learning_session.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 31.03.2017.
 */
@Data
@Getter
@ToString(exclude="themes")
@Entity
@Table(name = "scheme")
public class Scheme implements Serializable {

        private static final long serialVersionUID = 7737781206445619941L;

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @OneToMany(fetch = FetchType.EAGER, mappedBy = "scheme")
        private List<SchemeTheme> themes = new ArrayList<>();

        // Seems to be: random/sequence/happy - 0/1/2
        @Column(name = "mode")
        private int mode;

        @Column(name="allow_help")
        private boolean isHelpAllowed;

        @Column(name="dispright")
        private boolean isRightAnswerDisplayed;

        @Column(name="pyramid")
        private boolean isPyramidModeEnabled;

        @Column(name = "duration")
        private int duration;

        @Column(name = "m3")
        private int grade3StartsFrom;

        @Column(name = "m4")
        private int grade4StartsFrom;

        @Column(name = "m5")
        private int grade5StartsFrom;

        @Column(name = "n_hints")
        private int hintsQuantity;

        @Column(name = "k2")
        private double coefficientForLevel2;

        @Column(name = "k3")
        private double coefficientForLevel3;

        @Column(name = "timeleft")
        private long timeDuringWhichToKeepSessionInformation;

        @Column(name = "ip_mask")
        private String maskIPAddress;

        @Column(name="enableskip")
        private boolean isSkippingEnabled;

        @Column(name="main")
        private boolean isMainScheme;

        @Column(name="enabled")
        private boolean isEnabled;

        @Column(name="owner")
        private String owner;

        @Column(name="anonymous")
        private boolean isAvailableForAnonymousUser;

        @Column(name = "groups")
        private String studentGroups;

        @Column(name = "students")
        private String classRooms;

        @Column(name="hint_after")
        private boolean isHintAfterAnswerEnabled;

        // Always false de-facto
        @Column(name="never_expires")
        private boolean isSessionNeverExpires;

        @Column(name="allow_diff_ip")
        private boolean isDifferentIPAddressesAllowed;

        @Column(name="allow_view_log")
        private boolean isViewLogAllowed;

        @Column(name = "inet")
        private boolean isInternetAccessAllowed;

        @Column(name = "disp_mark")
        private boolean isDisplayMarkEnabled;

        @Column(name = "disp_percent")
        private boolean isDisplayPercentEnabled;

}
