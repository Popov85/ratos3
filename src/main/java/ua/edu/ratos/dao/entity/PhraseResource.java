package ua.edu.ratos.dao.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "phrase_resource")
@Cacheable
public class PhraseResource {

    @Id
    @Column(name = "phrase_id")
    private Long phraseId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "phrase_id")
    private Phrase phrase;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;
}
