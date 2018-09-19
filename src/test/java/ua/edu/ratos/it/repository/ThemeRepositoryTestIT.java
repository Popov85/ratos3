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
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.repository.ThemeRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ThemeRepositoryTestIT {

    @Autowired
    private ThemeRepository themeRepository;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByCourseIdTest() {
        Assert.assertEquals(21, themeRepository.findAll().size());
        Set<Theme> themes = themeRepository.findAllByCourseId(1L);
        Assert.assertEquals(3, themes.size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Assert.assertEquals(21, themeRepository.findAll().size());
        Page<Theme> themes = themeRepository.findByDepartmentId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(5, themes.getContent().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByFacultyIdTest() {
        Assert.assertEquals(21, themeRepository.findAll().size());
        Page<Theme> themes = themeRepository.findByFacultyId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(7, themes.getContent().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrganisationIdTest() {
        Assert.assertEquals(21, themeRepository.findAll().size());
        Page<Theme> themes = themeRepository.findByOrganisationId(1L, PageRequest.of(0, 20));
        Assert.assertEquals(11, themes.getContent().size());
    }
}
