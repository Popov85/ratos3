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
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.repository.ThemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ThemeRepositoryTestIT {

    @Autowired
    private ThemeRepository themeRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByIdTest() {
        Theme theme = themeRepository.findForSecurityById(1L);
        Assert.assertEquals(1L, theme.getAccess().getAccessId().longValue());
        Assert.assertEquals(1L, theme.getStaff().getStaffId().longValue());
        Assert.assertEquals(1L, theme.getStaff().getDepartment().getDepId().longValue());
    }

    //----------------------------SCHEMES TABLE----------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        List<Theme> themes = themeRepository.findAllByStaffId(1L, PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(11, themes.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        List<Theme> themes = themeRepository.findAllByStaffIdAndNameContains(4L, "Farm", PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(3, themes.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Theme> themes = themeRepository.findAllByDepartmentId(3L, PageRequest.of(0, 100));
        Assert.assertEquals(5, themes.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameContainsTest() {
        Page<Theme> themes = themeRepository.findAllByDepartmentIdAndNameContains(3L, "advanced", PageRequest.of(0, 100));
        Assert.assertEquals(2, themes.getContent().size());
    }

    //----------------------------QUESTIONS TABLE----------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForQuestionsTableByStaffIdTest() {
        List<Theme> themes = themeRepository.findAllForQuestionsTableByStaffId(1L, PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(11, themes.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForQuestionsTableByStaffIdAndNameContainsTest() {
        List<Theme> themes = themeRepository.findAllForQuestionsTableByStaffIdAndNameContains(4L, "Farm", PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(3, themes.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForQuestionsTableByDepartmentIdTest() {
        Page<Theme> themes = themeRepository.findAllForQuestionsTableByDepartmentId(3L, PageRequest.of(0, 100));
        Assert.assertEquals(5, themes.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForQuestionsTableByDepartmentIdAndNameContainsTest() {
        Page<Theme> themes = themeRepository.findAllForQuestionsTableByDepartmentIdAndNameContains(3L, "advanced", PageRequest.of(0, 100));
        Assert.assertEquals(2, themes.getContent().size());
    }

}
