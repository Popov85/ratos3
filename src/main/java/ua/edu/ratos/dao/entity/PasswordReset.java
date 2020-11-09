package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "password_reset")
@NoArgsConstructor
public class PasswordReset {

    private static final long serialVersionUID = 1L;

    public PasswordReset(String email, String secret, String status) {
        this.email = email;
        this.secret = secret;
        this.status = status;
    }

    @Id
    @NaturalId
    @Column(name = "email")
    private String email;

    @Column(name = "secret")
    private String secret;

    @CreationTimestamp
    @Column(name = "when_requested", columnDefinition = "TIMESTAMP")
    private LocalDateTime whenRequested;

    @Column(name = "status")
    private String status;
}
