package ua.edu.ratos.service.dto.out.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.StudMinOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GamerOutDto {

    private StudMinOutDto student;

    // Title based on achievements;
    private String label = "No label";

    // nullable
    private TotalAchievementsOutDto total;

    // nullable
    private WeeklyAchievementsOutDto weekly;

}
