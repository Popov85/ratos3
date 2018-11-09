package ua.edu.ratos.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.Staff;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(exclude = "staff")
@Entity
@Table(name = "settings_fbq")
@Cacheable
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


    // Checks the match provided case insensitive
    public boolean checkCaseInsensitiveMatch(final String enteredPhrase, final List<String> acceptedPhrases) {
        final String enteredPhraseLowerCase = enteredPhrase.toLowerCase();
        List<String> acceptedPhrasesLowerCase = acceptedPhrases
                .stream()
                .map(p -> p.toLowerCase())
                .collect(Collectors.toList());
        if (acceptedPhrasesLowerCase.contains(enteredPhraseLowerCase)) return true;
        return false;
    }

    // Checks the match if single typo is allowed
    public boolean checkSingleTypoMatch(final String enteredPhrase, final List<String> acceptedPhrases) {
        for (String acceptedPhrase : acceptedPhrases) {
            // Do anything only if the length is equal
            if (acceptedPhrase.length()==enteredPhrase.length()) {
                for (int i = 0; i < acceptedPhrase.length(); i++) {
                    // Algorithm: remove chars one by one and compare strings without this char, if equal then put that a typo occurred
                    StringBuilder sbAcceptedPhrase = new StringBuilder(acceptedPhrase);
                    StringBuilder sbEnteredPhrase = new StringBuilder(enteredPhrase);
                    String typoAcceptedPhrase = sbAcceptedPhrase.deleteCharAt(i).toString();
                    String typoEnteredPhrase = sbEnteredPhrase.deleteCharAt(i).toString();
                    if (typoAcceptedPhrase.equals(typoEnteredPhrase)) return true;
                    if (!isCaseSensitive) {
                        if (typoAcceptedPhrase.equalsIgnoreCase(typoEnteredPhrase)) return true;
                    }
                }
            }
        }
        return false;
    }
}
