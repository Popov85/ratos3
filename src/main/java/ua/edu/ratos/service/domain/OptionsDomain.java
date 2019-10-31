package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class OptionsDomain {

    private Long optId;

    private String name;

    // -- During session--

    private boolean displayQuestionsLeft;

    private boolean displayBatchesLeft;

    private boolean displayCurrentScore;

    private boolean displayEffectiveScore;

    private boolean displayProgress;

    // Encouraging message for 100% result on a batch
    private boolean displayMotivationalMessages;

    //-- At the End--

    private boolean displayResultScore;

    private boolean displayResultMark;

    private boolean displayTimeSpent;

    private boolean displayResultOnThemes;

    private boolean displayResultOnQuestions;

}
