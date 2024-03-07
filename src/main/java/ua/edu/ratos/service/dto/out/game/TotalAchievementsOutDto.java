package ua.edu.ratos.service.dto.out.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TotalAchievementsOutDto {

    private int totalPoints;

    private int totalBonuses;

    private int totalWins;
}
