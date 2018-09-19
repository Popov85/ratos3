package ua.edu.ratos.it.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.*;
import ua.edu.ratos.domain.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.SchemeService;

import javax.annotation.PostConstruct;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SchemeServiceTestIT {

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemeRepository schemeRepository;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/scheme_theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void reOrderTest() throws Exception {
        // 1. Given 8 themes associated with a scheme
        // 2. Reorder elements by putting 2d index element to the top and 5th index element to the bottom
        //    {1, 2, 3, 4, 5, 6, 7, 8} - > {3, 1, 2, 4, 5, 7, 8, 6}
        // 3. Make sure 8 of them are still present after manipulations and resulting lists are equal?
        // 4. Observe indexes re-built!
        final List<Long> edit = Arrays.asList(3L, 1L, 2L, 4L, 5L, 7L, 8L, 6L);
        schemeService.reOrder(1L, edit);
        final List<SchemeTheme> schemeThemes = schemeRepository.findByIdWithThemes(1L).getSchemeThemes();
        final List<Long> actual = schemeThemes.stream().map(SchemeTheme::getSchemeThemeId)
                .collect(Collectors.toList());
        Assert.assertEquals(8, actual.size());
        Assert.assertEquals(edit, actual);
        //schemeThemes.forEach(System.out::println);
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/scheme_theme_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIndexTest() throws Exception {
        // 1. Given 8 themes associated with a scheme
        // 2. Delete the 4th one
        // 3. Make sure only 7 of they are left, make sure the Scheme is still completed
        // 4. Observe indexes updated!
        Assert.assertEquals(8, schemeRepository.findByIdWithThemes(1L).getSchemeThemes().size());
        schemeService.deleteByIndex(1L, 4);
        final Scheme foundScheme = schemeRepository.findByIdWithThemes(1L);
        Assert.assertEquals(7, foundScheme.getSchemeThemes().size());
        Assert.assertTrue(foundScheme.isCompleted());
        //schemeThemes.forEach(System.out::println);
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/scheme_theme_test_data.sql", "/scripts/scheme_theme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIndexLastElementTest() throws Exception {
        // 1. Given 1 theme associated with a scheme
        // 2. Delete it single theme
        // 3. Make sure 0 of them are left
        // 4. Make sure the Scheme became incomplete
        Assert.assertEquals(1, schemeRepository.findByIdWithThemes(1L).getSchemeThemes().size());
        schemeService.deleteByIndex(1L, 0);
        final Scheme foundScheme = schemeRepository.findByIdWithThemes(1L);
        Assert.assertEquals(0, foundScheme.getSchemeThemes().size());
        Assert.assertFalse(foundScheme.isCompleted());
        foundScheme.getSchemeThemes().forEach(System.out::println);
    }
}
