package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name="language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "lang_id")
    private Long langId;

    @Column(name = "name")
    private String name;

    @Column(name = "eng_abbreviation")
    private String abbreviation;
}
