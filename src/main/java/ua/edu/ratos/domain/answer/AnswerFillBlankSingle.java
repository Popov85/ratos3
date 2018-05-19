package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * Free-word answer, only one blank to fill acceptable, within this blank several words are acceptable
 * @author Andrey P.
 */
@Setter
@Getter
@ToString
public class AnswerFillBlankSingle implements Answer {
    private long answerId;
    /**
     * English - eng, French - fr, Polish - pl, Ukrainian - ukr, Russian - ru, etc.
     */
    private String lang = "en";
    private short wordsLimit = 1;
    private short symbolsLimit = 1;
    private boolean isNumeric;
    private boolean isTypoAllowed;
    private boolean isCaseSensitive;
    private List<String> acceptedPhrases;

    @Override
    public boolean isValid() {
        if (this.lang == null) return false;
        if (this.lang.isEmpty()) return false;
        if (this.wordsLimit <1) return false;
        if (this.symbolsLimit <1) return false;
        if (this.acceptedPhrases==null) return false;
        if (this.acceptedPhrases.isEmpty()) return false;
        return true;
    }
}
