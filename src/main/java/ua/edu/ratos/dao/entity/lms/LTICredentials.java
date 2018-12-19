package ua.edu.ratos.dao.entity.lms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lti_credentials")
public class LTICredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "credentials_id")
    private Long credId;

    @Column(name = "lti_consumer_key")
    private String key;

    @Column(name = "lti_client_secret")
    private String secret;
}
