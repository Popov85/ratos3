package ua.edu.ratos.dao.entity.lms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lti_credentials")
public class LTICredentials implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "credentials_id")
    private Long credId;

    @Column(name = "lti_consumer_key")
    private String key;

    @Column(name = "lti_client_secret")
    private String secret;
}
