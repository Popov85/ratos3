package ua.edu.ratos.domain.entity.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.Staff;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = "staff")
@Entity
@Table(name = "settings_fbq")
public class SettingsAnswerFillBlank {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="set_id")
    private Long settingsId;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "lang_id")
    protected Language lang;

    @Column(name="words_limit")
    private short wordsLimit = 1;

    @Column(name="symbols_limit")
    private short symbolsLimit = 1;

    @Column(name="is_numeric")
    private boolean isNumeric;

    @Column(name="is_typo_allowed")
    private boolean isTypoAllowed;

    @Column(name="is_case_sensitive")
    private boolean isCaseSensitive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    protected Staff staff;
}
