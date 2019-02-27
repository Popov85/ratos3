package ua.edu.ratos.it.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.game.Game;
import ua.edu.ratos.dao.repository.game.GameRepository;
import ua.edu.ratos.it.ActiveProfile;
import java.util.Optional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class GameRepositoryTestIT {

    @Autowired
    private GameRepository gameRepository;

    //-----------------------------------------------------Increment----------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/game_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        //Given Game entry for userId = 2L with totalWins = 1 (see sql script)

        gameRepository.incrementWins(2L);

        Optional<Game> game = gameRepository.findById(2L);
        assertNotNull(game);
        assertEquals(2, game.get().getTotalWins());
    }
}
