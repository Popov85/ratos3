package ua.edu.ratos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.dto.out.game.GamerOutDto;
import ua.edu.ratos.service.dto.out.game.AllTimesGamerOutDto;
import ua.edu.ratos.service.dto.out.game.WeeklyGamerOutDto;
import ua.edu.ratos.service.dto.out.game.WinnerOutDto;
import ua.edu.ratos.service.session.GameService;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    //-------------------------------------Gamer's current rating (weekly + total)--------------------------------------

    @GetMapping(value = "/student/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GamerOutDto> findMyGamingRating() {
        return ResponseEntity.ok(gameService.findMyGamingRating());
    }

    //-----------------------------------------------TOP all times gamers-----------------------------------------------

    @GetMapping(value = {"/student/gamers", "/department/gamers"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<AllTimesGamerOutDto> findBestGamers(@SortDefault.SortDefaults({
            @SortDefault(sort = "totalPoints", direction = Sort.Direction.DESC),
            @SortDefault(sort = "totalBonuses", direction = Sort.Direction.DESC),
            @SortDefault(sort = "totalWins", direction = Sort.Direction.DESC)}) @PageableDefault(value = 50) Pageable pageable) {
        return gameService.findBestGamers(pageable);
    }

    //------------------------------------------------TOP weekly gamers-------------------------------------------------

    @GetMapping(value = {"/student/weekly-gamers", "/department/weekly-gamers"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<WeeklyGamerOutDto> findWeeklyGamers(@SortDefault.SortDefaults({
            @SortDefault(sort = "weekPoints", direction = Sort.Direction.DESC),
            @SortDefault(sort = "weekBonuses", direction = Sort.Direction.DESC),
            @SortDefault(sort = "weekTimeSpent", direction = Sort.Direction.ASC)}) @PageableDefault(value = 50) Pageable pageable) {
        return gameService.findWeeklyGamers(pageable);
    }

    //----------------------------------------------Last week winners rating--------------------------------------------

    @GetMapping(value = {"/student/weekly-winners", "/department/weekly-winners"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<WinnerOutDto> findLastWeekWinners(@SortDefault.SortDefaults({
            @SortDefault(sort = "wonPoints", direction = Sort.Direction.DESC),
            @SortDefault(sort = "wonBonuses", direction = Sort.Direction.DESC),
            @SortDefault(sort = "wonTimeSpent", direction = Sort.Direction.ASC)}) @PageableDefault(value = 50) Pageable pageable) {
        return gameService.findLastWeekWinners(pageable);
    }
}
