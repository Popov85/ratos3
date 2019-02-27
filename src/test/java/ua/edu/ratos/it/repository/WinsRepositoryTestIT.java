package ua.edu.ratos.it.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.game.Wins;
import ua.edu.ratos.dao.repository.game.WinsRepository;
import ua.edu.ratos.it.ActiveProfile;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class WinsRepositoryTestIT {

    @Autowired
    private WinsRepository winsRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/wins_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllWinnersSinceTest() {
        // Given: 10 students, 3 winners of last week, many other winner for previous weeks,
        // It's now Wednesday and I want to know who won last week (Sunday 2019-02-24)?
        String date = "2019-02-27";
        //default, ISO_LOCAL_DATE
        LocalDate localDate = LocalDate.parse(date);
        // who won last week?
        Page<Wins> result = winsRepository.findAllWinnersSince(localDate.minusDays(7), PageRequest.of(0, 10));
        assertEquals(3, result.getContent().size());
        // winners should be students with IDs {2, 3, 4}
        List<Long> winnersLastWeek = result.getContent().stream().map(w -> w.getStudent().getStudId()).collect(Collectors.toList());
        assertThat(winnersLastWeek, containsInAnyOrder(2L, 3L, 4L));
    }
}
