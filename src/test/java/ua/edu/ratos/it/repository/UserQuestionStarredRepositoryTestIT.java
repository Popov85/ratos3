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
import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.dao.repository.UserQuestionStarredRepository;
import ua.edu.ratos.it.ActiveProfile;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserQuestionStarredRepositoryTestIT {

    @Autowired
    private UserQuestionStarredRepository userQuestionStarredRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/starred_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByUserIdTest() {
        Page<UserQuestionStarred> result = userQuestionStarredRepository.findAllByUserId(2L, PageRequest.of(0, 10));
        assertEquals(5, result.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/starred_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countByUserIdTest() {
        long count = userQuestionStarredRepository.countByUserId(3L);
        assertEquals(5, count);
    }
}
