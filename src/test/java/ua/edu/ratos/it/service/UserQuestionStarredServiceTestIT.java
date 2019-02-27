package ua.edu.ratos.it.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.session.UserQuestionStarredService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserQuestionStarredServiceTestIT {

    @Autowired
    private UserQuestionStarredService userQuestionStarredService;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/starred_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneByQuestionIdTest() {
        QuestionSessionOutDto dto = userQuestionStarredService.findOneByQuestionId(1L);
        System.out.println("dto = "+dto);
        assertNotNull(dto);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/starred_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByUserIdTest() {
        Page<QuestionSessionMinOutDto> result = userQuestionStarredService.findAllByUserId(PageRequest.of(0, 10));
        result.getContent().forEach(System.out::println);
        assertEquals(5, result.getContent().size());
    }
}
