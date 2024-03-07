package ua.edu.ratos.service.dto.out.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.StudMinOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class WinnerOutDto {

    // who is the winner
    private StudMinOutDto student;

    // the winner won with the points
    private int points;

    // the winner won with bonuses
    private int bonuses;

    // the winner spent time of all weekly learning sessions
    private long timeSpent;
}
