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
public class AllTimesGamerOutDto {

    private StudMinOutDto student;

    // User's title based on achievements
    private String label;

    private TotalAchievementsOutDto totalAchievements;

}
