package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class OptionsMinOutDto {

    private Long optId;

    private String name;

    // -- Session--

    private boolean displayCurrentScore;

    private boolean displayEffectiveScore;

    private boolean displayProgress;

    // Messages for 100% result on a batch
    private boolean displayMotivationalMessages;

    //-- End--

    private boolean displayResultScore;

    private boolean displayResultMark;

    private boolean displayResultOnThemes;

    private boolean displayResultOnQuestions;
}
