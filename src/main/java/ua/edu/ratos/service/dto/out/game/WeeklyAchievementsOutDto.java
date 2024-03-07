package ua.edu.ratos.service.dto.out.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class WeeklyAchievementsOutDto {

    private int weekPoints;

    private int weekBonuses;

    private int weekStrike;

    private long weekTimeSpent;
}
