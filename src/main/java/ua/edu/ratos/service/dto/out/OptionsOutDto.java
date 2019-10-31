package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class OptionsOutDto {

    private Long optId;

    private String name;

    private boolean displayQuestionsLeft;

    private boolean displayBatchesLeft;

    private boolean displayCurrentScore;

    private boolean displayEffectiveScore;

    private boolean displayProgress;

    private boolean displayMotivationalMessages;

    private boolean displayResultScore;

    private boolean displayResultMark;

    private boolean displayTimeSpent;

    private boolean displayResultOnThemes;

    private boolean displayResultOnQuestions;

    private StaffMinOutDto staff;
}
