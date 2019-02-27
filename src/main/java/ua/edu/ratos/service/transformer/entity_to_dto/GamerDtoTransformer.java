package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.dao.entity.game.Gamer;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.service.dto.out.StudMinOutDto;
import ua.edu.ratos.service.dto.out.game.GamerOutDto;
import ua.edu.ratos.service.session.GameLabelResolver;

import java.util.Optional;

@Component
public class GamerDtoTransformer {

    private UserMinDtoTransformer userMinDtoTransformer;

    private ClassMinDtoTransformer classMinDtoTransformer;

    private FacultyMinDtoTransformer facultyMinDtoTransformer;

    private OrganisationMinDtoTransformer organisationMinDtoTransformer;

    private GameLabelResolver gameLabelResolver;

    private TotalAchievementsDtoTransformer totalAchievementsDtoTransformer;

    private WeeklyAchievementsDtoTransformer weeklyAchievementsDtoTransformer;

    @Autowired
    public void setClassMinDtoTransformer(ClassMinDtoTransformer classMinDtoTransformer) {
        this.classMinDtoTransformer = classMinDtoTransformer;
    }

    @Autowired
    public void setFacultyMinDtoTransformer(FacultyMinDtoTransformer facultyMinDtoTransformer) {
        this.facultyMinDtoTransformer = facultyMinDtoTransformer;
    }

    @Autowired
    public void setUserMinDtoTransformer(UserMinDtoTransformer userMinDtoTransformer) {
        this.userMinDtoTransformer = userMinDtoTransformer;
    }

    @Autowired
    public void setOrganisationMinDtoTransformer(OrganisationMinDtoTransformer organisationMinDtoTransformer) {
        this.organisationMinDtoTransformer = organisationMinDtoTransformer;
    }

    @Autowired
    public void setGameLabelResolver(GameLabelResolver gameLabelResolver) {
        this.gameLabelResolver = gameLabelResolver;
    }

    @Autowired
    public void setTotalAchievementsDtoTransformer(TotalAchievementsDtoTransformer totalAchievementsDtoTransformer) {
        this.totalAchievementsDtoTransformer = totalAchievementsDtoTransformer;
    }

    @Autowired
    public void setWeeklyAchievementsDtoTransformer(WeeklyAchievementsDtoTransformer weeklyAchievementsDtoTransformer) {
        this.weeklyAchievementsDtoTransformer = weeklyAchievementsDtoTransformer;
    }

    public GamerOutDto toDto(@NonNull final Gamer entity) {
        Optional<Game> game = entity.getGame();
        Optional<Week> week = entity.getWeek();
        GamerOutDto dto = new GamerOutDto().setStudent(getStud(entity));
        if (game.isPresent()) {
            dto.setLabel(gameLabelResolver.getLabel(game.get().getTotalWins()));
            dto.setTotal(totalAchievementsDtoTransformer.toDto(game.get()));
        }
        if (week.isPresent()) dto.setWeekly(weeklyAchievementsDtoTransformer.toDto(week.get()));
        return dto;
    }

    private StudMinOutDto getStud(@NonNull final Gamer entity) {
        return new StudMinOutDto()
                .setStudId(entity.getStudId())
                .setUser(userMinDtoTransformer.toDto(entity.getUser()))
                .setStudentClass(classMinDtoTransformer.toDto(entity.getStudentClass()))
                .setFaculty(facultyMinDtoTransformer.toDto(entity.getFaculty()))
                .setOrganisation(organisationMinDtoTransformer.toDto(entity.getOrganisation()))
                .setEntranceYear(entity.getEntranceYear());
    }
}
