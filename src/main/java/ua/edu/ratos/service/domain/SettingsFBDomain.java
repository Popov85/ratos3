package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class SettingsFBDomain {

    private Long settingsId;

    private String name;

    protected String lang;

    private short wordsLimit = 1;

    private short symbolsLimit = 1;

    private boolean isNumeric;

    private boolean isTypoAllowed;

    private boolean isCaseSensitive;

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
            if (acceptedPhrase.length()==enteredPhrase.length()) {
                // Equal length
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
            } else {
                // Maybe entered phrase lacks single symbol?
                for (int i = 0; i < acceptedPhrase.length(); i++) {
                    // Algorithm: remove chars one by one and compare strings without this char, if equal then put that a typo occurred
                    StringBuilder sbAcceptedPhrase = new StringBuilder(acceptedPhrase);
                    StringBuilder sbEnteredPhrase = new StringBuilder(enteredPhrase);
                    String typoAcceptedPhrase = sbAcceptedPhrase.deleteCharAt(i).toString();
                    String typoEnteredPhrase = sbEnteredPhrase.toString();
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
