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

    //--------------------------------------------------Staff TABLE-----------------------------------------------------

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
    public void findAllByStaffIdAndNameStartsTest() {
        List<Theme> themes = themeRepository.findAllByStaffIdAndNameStarts(4L, "Farm", PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(3, themes.size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameLettersContainsTest() {
        List<Theme> themes = themeRepository.findAllByStaffIdAndNameLettersContains(5L, "(advanced)", PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(2, themes.size());
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
    public void findAllByDepartmentIdAndNameStartsTest() {
        Page<Theme> themes = themeRepository.findAllByDepartmentIdAndNameStarts(3L, "Advanced", PageRequest.of(0, 100));
        Assert.assertEquals(2, themes.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        Page<Theme> themes = themeRepository.findAllByDepartmentIdAndNameLettersContains(3L, "Med", PageRequest.of(0, 100));
        Assert.assertEquals(5, themes.getContent().size());
    }

}
