package ua.zp.zsmu.ratos.learning_session.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andrey on 31.03.2017.
 */
@Data
@Getter
@ToString(exclude="themes")
@Entity
@Table(name = "scheme")
public class Scheme implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @OneToMany(mappedBy = "scheme")
        private List<SchemeTheme> themes = new ArrayList<>();
        //private Set<SchemeTheme> themes = new HashSet<>();

        @Column(name = "mode")
        private Integer mode;

        @Column(name="allow_help")
        private boolean isHelpAllowed;

        @Column(name="dispright")
        private boolean isRightAnswerDisplayed;

        @Column(name="pyramid")
        private boolean isPyramidModeEnabled;

        @Column(name = "duration")
        private Integer duration;

        @Column(name = "m3")
        private Integer grade3StartsFrom;

        @Column(name = "m4")
        private Integer grade4StartsFrom;

        @Column(name = "m5")
        private Integer grade5StartsFrom;

        @Column(name = "n_hints")
        private Integer hintsQuantity;

        @Column(name = "k2")
        private Double coefficientForLevel2;

        @Column(name = "k3")
        private Double coefficientForLevel3;

        @Column(name = "timeleft")
        private Long timeDuringWhichToKeepSessionInformation;

        @Column(name = "ip_mask")
        private String maskIPAddress;

        @Column(name="enableskip")
        private boolean isSkippingEnabled;

        @Column(name="main")
        private boolean isMainScheme;

        @Column(name="enabled")
        private boolean isEnabled;

        /*@OneToOne(fetch = FetchType.LAZY)
        @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
        @JoinColumn(name="user", unique=true, nullable=false, updatable=false)*/
        @Column(name="owner")
        private String owner;

        @Column(name="anonymous")
        private boolean isAvailableForAnonymousUser;

        // String
        @Column(name = "groups")
        private String studentGroups;
        /*@OneToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "scheme")
        private Set<Group> studentGroups;*/

        // String
        @Column(name = "students")
        private String classRooms;
        /*@OneToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "scheme")
        private Set<ClassRoom> classRooms;*/

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
