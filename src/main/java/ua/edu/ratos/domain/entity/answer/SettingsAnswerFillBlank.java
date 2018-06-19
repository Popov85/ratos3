package ua.edu.ratos.domain.entity.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Language;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name = "settings_fbq")
public class SettingsAnswerFillBlank {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="set_id")
    private Long settingsId;

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
}
