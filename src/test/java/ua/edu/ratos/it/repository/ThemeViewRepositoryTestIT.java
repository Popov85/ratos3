package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.ThemeView;
import ua.edu.ratos.domain.repository.ThemeViewRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.Set;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ThemeViewRepositoryTestIT {

    @Autowired
    private ThemeViewRepository themeViewRepository;


    @Test
    @Sql(scripts = "/scripts/theme_view_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByThemeIdTest() {
        themeViewRepository.findAll().forEach(System.out::println);
        final Set<ThemeView> questions = themeViewRepository.findAllByThemeId(1L);
        Assert.assertEquals(3, questions.size());
    }

    @Test
    @Sql(scripts = "/scripts/theme_view_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByCourseIdTest() {
        final Set<ThemeView> questions = themeViewRepository.findAllByCourseId(2L);
        questions.forEach(System.out::println);
        Assert.assertEquals(3, questions.size());
    }

    @Test
    @Sql(scripts = "/scripts/theme_view_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByCourseIdAndFirstThemeLettersTest() {
        final Set<ThemeView> questions = themeViewRepository.findAllByCourseIdAndThemeLettersContains(1L, "unique");
        questions.forEach(System.out::println);
        Assert.assertEquals(2, questions.size());
    }

    @Test
    @Sql(scripts = "/scripts/theme_view_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        final Page<ThemeView> questions = themeViewRepository.findAllByDepartmentId(1L, PageRequest.of(0, 50));
        questions.forEach(System.out::println);
        Assert.assertEquals(8, questions.getContent().size());
    }

    @Test
    @Sql(scripts = "/scripts/theme_view_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndFirstThemeLettersTest() {
        final Set<ThemeView> questions = themeViewRepository.findAllByDepartmentIdAndThemeLettersContains(1L, "unique");
        questions.forEach(System.out::println);
        Assert.assertEquals(2, questions.size());
    }

    @Test
    @Sql(scripts = "/scripts/theme_view_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrganisationIdTest() {
        final Page<ThemeView> questions = themeViewRepository.findAllByOrganisationId(2L, PageRequest.of(0, 50));
        questions.forEach(System.out::println);
        Assert.assertEquals(2, questions.getContent().size());
    }

    @Test
    @Sql(scripts = "/scripts/theme_view_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrganisationIdAndFirstThemeLettersTest() {
        final Set<ThemeView> questions = themeViewRepository.findAllByOrganisationIdAndThemeLettersContains(2L, "special");
        questions.forEach(System.out::println);
        Assert.assertEquals(1, questions.size());
    }
}
