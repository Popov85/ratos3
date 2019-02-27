package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.game.Gamer;
import ua.edu.ratos.dao.repository.game.GamerRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class GamerRepositoryTestIT {

    @Autowired
    private GamerRepository gamerRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/gamer_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        Optional<Gamer> gamer = gamerRepository.findById(2L);
        Assert.assertTrue(gamer.isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/gamer_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditNoTotalTest() {
        Optional<Gamer> gamer = gamerRepository.findById(3L);
        Assert.assertTrue(gamer.isPresent());
    }
}
