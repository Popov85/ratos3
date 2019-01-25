package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.ThemeView;
import ua.edu.ratos.dao.repository.ThemeViewRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.Set;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ThemeViewRepositoryTestIT {

    @Autowired
    private ThemeViewRepository themeViewRepository;


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_view_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByThemeIdTest() {
        final Set<ThemeView> result = themeViewRepository.findAllByThemeId(1L);
        //result.forEach(System.out::println);
        Assert.assertEquals(2, result.size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_view_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepIdTest() {
        final Set<ThemeView> result = themeViewRepository.findAllByDepartmentId(1L);
        //result.forEach(System.out::println);
        Assert.assertEquals(11, result.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_view_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepIdAndThemeLettersContainsTest() {
        final Set<ThemeView> result = themeViewRepository.findAllByDepartmentIdAndThemeLettersContains(1L, "advanced");
        //result.forEach(System.out::println);
        Assert.assertEquals(2, result.size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_view_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepAndCourseIdTest() {
        final Set<ThemeView> result = themeViewRepository.findAllByDepartmentIdAndCourseId(1L, 1L);
        //result.forEach(System.out::println);
        Assert.assertEquals(7, result.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_view_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepAndCourseIdAndThemeLettersContainsTest() {
        final Set<ThemeView> result = themeViewRepository.findAllByDepartmentIdAndCourseIdAndThemeLettersContains(2L, 3L, "required");
        //result.forEach(System.out::println);
        Assert.assertEquals(3, result.size());
    }
}
