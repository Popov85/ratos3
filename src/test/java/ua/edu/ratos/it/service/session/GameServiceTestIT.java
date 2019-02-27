package ua.edu.ratos.it.service.session;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.dao.entity.game.Week;
import ua.edu.ratos.dao.entity.game.Wins;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.UserDomain;
import ua.edu.ratos.service.session.GameService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class GameServiceTestIT {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private GameService gameService;

    //-------------------------------------------------Check and get points---------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/game_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void checkAndGetPointsNotEnoughPercentsTest() {
        Optional<Integer> integer = gameService.checkAndGetPoints(2L, 1L, 79.5);
        assertTrue(integer.isPresent());
        assertEquals(new Integer(0), integer.get());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/game_test_data_one.sql", "/scripts/game_results_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void checkAndGetPointsNotFirstAttemptTest() {
        Optional<Integer> integer = gameService.checkAndGetPoints(2L, 1L, 85.6);
        assertTrue(integer.isPresent());
        assertEquals(new Integer(0), integer.get());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/game_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void checkAndGetPointsHighestPercentsTest() {
        Optional<Integer> integer = gameService.checkAndGetPoints(3L, 1L, 99.9);
        assertTrue(integer.isPresent());
        assertEquals(new Integer(5), integer.get());
    }


    //-------------------------------------------------------Save points------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void savePointsCreateTest() {
        // Given: userId = 2L scored enough for being granted points first time ever
        // no entries for this user in week/game tables before
        ResultDomain resultDomain = new ResultDomain()
                .setUser(new UserDomain(2L, "TestName", "TestSurname"))
                .setPoints(5)
                .setTimeSpent(781);
        // do save points
        gameService.savePoints(resultDomain);

        // expected entry in week table, expected entry in game table
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId=:studId").setParameter("studId", 2L).getSingleResult();
        assertNotNull(week);
        assertEquals(5, week.getWeekPoints());
        assertEquals(1, week.getWeekStrike());
        assertEquals(781, week.getWeekTimeSpent());

        final Game game = (Game) em.createQuery("select g from Game g where g.gameId=:studId").setParameter("studId", 2L).getSingleResult();
        assertNotNull(game);
        assertEquals(5, game.getTotalPoints());
        assertEquals(0, game.getTotalWins());
        assertEquals(0, game.getTotalBonuses());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/game_test_data_one.sql", "/scripts/week_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void savePointsUpdateTest() {
        // Given: userId = 2L scored enough for being granted points next time in the week
        // there were entries for this user in week/game tables before(!)
        ResultDomain resultDomain = new ResultDomain()
                .setUser(new UserDomain(2L, "TestName", "TestSurname"))
                .setPoints(3)
                .setTimeSpent(164);
        // do save points
        gameService.savePoints(resultDomain);

        // expected updated entries in week table and in game table
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId=:studId").setParameter("studId", 2L).getSingleResult();
        assertNotNull(week);
        assertEquals(13, week.getWeekPoints());
        assertEquals(2, week.getWeekStrike());
        assertEquals(2200, week.getWeekTimeSpent());

        final Game game = (Game) em.createQuery("select g from Game g where g.gameId=:studId").setParameter("studId", 2L).getSingleResult();
        assertNotNull(game);
        assertEquals(103, game.getTotalPoints());
        assertEquals(1, game.getTotalWins());
        assertEquals(20, game.getTotalBonuses());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/game_test_data_one.sql", "/scripts/week_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void savePointsStrikeTest() {
        // Given: userId = 3L scored enough for being granted points 3d time in the week (strike)
        // there were entries for this user in week/game tables before(!)
        ResultDomain resultDomain = new ResultDomain()
                .setUser(new UserDomain(3L, "TestName", "TestSurname"))
                .setPoints(1)
                .setTimeSpent(440);
        // do save points
        gameService.savePoints(resultDomain);

        // expected updated entries in week table (+bonuses, points, reset strike) and in game table (+bonuses and points)
        final Week week = (Week) em.createQuery("select w from Week w where w.weekId=:studId").setParameter("studId", 3L).getSingleResult();
        assertNotNull(week);
        assertEquals(21, week.getWeekPoints());
        assertEquals(0, week.getWeekStrike()); // =0
        assertEquals(20, week.getWeekBonuses()); //+10
        assertEquals(4590, week.getWeekTimeSpent()); //+440

        final Game game = (Game) em.createQuery("select g from Game g where g.gameId=:studId").setParameter("studId", 3L).getSingleResult();
        assertNotNull(game);
        assertEquals(101, game.getTotalPoints()); // +1
        assertEquals(1, game.getTotalWins());
        assertEquals(30, game.getTotalBonuses()); // +10
    }

    //-----------------------------------------------Quartz regular weeklyAchievements job------------------------------------------
    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/week_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void calculateAndSaveWeeklyWinnersFirstWeekTest() {
        gameService.calculateAndSaveWeeklyWinners();
        // top-3 weeklyAchievements winners
        List<Wins> result =(List<Wins>) em.createQuery("select w from Wins w").getResultList();
        assertEquals(3, result.size());
        // 9, 11, 18
        List<Long> winners = result.stream().map(w -> w.getStudent().getStudId()).collect(Collectors.toList());
        assertThat(winners, containsInAnyOrder(9L, 11L, 18L));
        // check updates of game's totalWins for these 3 winners
        final Game game9 = (Game) em.createQuery("select g from Game g where g.gameId=:studId").setParameter("studId", 9L).getSingleResult();
        final Game game11 = (Game) em.createQuery("select g from Game g where g.gameId=:studId").setParameter("studId", 11L).getSingleResult();
        final Game game18 = (Game) em.createQuery("select g from Game g where g.gameId=:studId").setParameter("studId", 18L).getSingleResult();
        assertEquals(1, game9.getTotalWins()); // +1
        assertEquals(1, game11.getTotalWins()); // +1
        assertEquals(1, game18.getTotalWins()); // +1
        // make sure the week table was emptied
        List<Week> emptyWeek = em.createQuery("select w from Week w").getResultList();
        assertTrue(emptyWeek.isEmpty());
    }
}
