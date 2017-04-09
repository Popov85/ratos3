package ua.zp.zsmu.ratos.learning_session.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Andrey on 4/8/2017.
 */
@Data
@Getter
@Setter
@Entity
@ToString(exclude="scheme")
@Table(name = "session")
public class Session {

        @Id
        @GeneratedValue(generator = "increment")
        @GenericGenerator(name = "increment", strategy = "increment")
        @Column(name = "SID")
        private Long sid;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name="scheme")
        private Scheme scheme;

        @Column(name="session")
        private byte[] session;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="EET")
        @Column(name="begins")
        private Date beginTime;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="EET")
        @Column(name="ends")
        private Date endTime;

        @Column(name="timeleft")
        private long timeLeft;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="EET")
        @Column(name="currtime")
        private Date lastSerialisationTime;

        @Column(name="ip")
        private String remoteIP;

        @Column(name="anonymous")
        private boolean isAnonymous;

        @Column(name="finished")
        private boolean isFinished;

        @Column(name="never_expires")
        private boolean isNeverExpires;

        /*@ManyToOne
        @JoinColumn(name="uid")
        private User user;*/
}
