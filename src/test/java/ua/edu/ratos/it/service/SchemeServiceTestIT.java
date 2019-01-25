package ua.edu.ratos.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.dao.entity.grade.SchemeFourPoint;
import ua.edu.ratos.dao.entity.grade.SchemeFreePoint;
import ua.edu.ratos.dao.entity.grade.SchemeTwoPoint;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.service.dto.in.SchemeInDto;
import ua.edu.ratos.service.SchemeService;
import javax.persistence.EntityManager;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SchemeServiceTestIT {

    private static final String JSON_NEW = "classpath:json/scheme_in_dto_new.json";

    private static final String FIND_WITH_COLLECTIONS = "select s from Scheme s left join fetch s.groups left join fetch s.themes t left join fetch t.settings where s.schemeId=:schemeId";
    private static final String FIND_GRADING_FOUR = "select s from SchemeFourPoint s join fetch s.fourPointGrading where s.schemeId=:schemeId";
    private static final String FIND_GRADING_FREE = "select s from SchemeFreePoint s join fetch s.freePointGrading where s.schemeId=:schemeId";
    private static final String FIND_GRADING_TWO = "select s from SchemeTwoPoint s join fetch s.twoPointGrading where s.schemeId=:schemeId";

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        SchemeInDto dto = objectMapper.readValue(json, SchemeInDto.class);
        final Long returnedId = schemeService.save(dto);
        Assert.assertEquals(1L, returnedId.longValue());
        // test insertion into scheme, group_scheme and scheme_theme, type_level
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertNotNull(foundScheme.getSettings());
        Assert.assertEquals(3, foundScheme.getGroups().size());
        Assert.assertEquals(3, foundScheme.getThemes().size());
        foundScheme.getThemes().forEach(t->Assert.assertEquals(3, t.getSettings().size()));
        // test insertion into scheme_four_point table
        final SchemeFourPoint foundFour =
                (SchemeFourPoint) em.createQuery(FIND_GRADING_FOUR)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundFour);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateNameTest() throws Exception {
        schemeService.updateName(1L, "Updated name");
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals("Updated name", foundScheme.getName());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateStrategyTest() throws Exception {
        schemeService.updateStrategy(1L, 2L);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals(2L, foundScheme.getStrategy().getStrId().longValue());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateIsActiveTest() throws Exception {
        schemeService.updateIsActive(1L, false);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertFalse(foundScheme.isActive());
    }

    // Gradings

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_grading.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateGradingNewDetailsTest() throws Exception {
        // Given gradId (same) 3L -> 3L; free_point 1L -> 2L
        schemeService.updateGrading(1L, 3L, 2L);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals(3L, foundScheme.getGrading().getGradingId().longValue());

        // test insertion into scheme_free_point table
        final SchemeFreePoint foundFree =
                (SchemeFreePoint) em.createQuery(FIND_GRADING_FREE)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertEquals(2L, foundFree.getFreePointGrading().getFreeId().longValue());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_grading.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateGradingNewTypeTest() throws Exception {
        // Given gradId (new) 3L -> 2L; free_point 1L -> two_point 1L
        schemeService.updateGrading(1L, 2L, 1L);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals(2L, foundScheme.getGrading().getGradingId().longValue());

        // test insertion into scheme_two_point table
        final SchemeTwoPoint foundTwo =
                (SchemeTwoPoint) em.createQuery(FIND_GRADING_TWO)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertEquals(1L, foundTwo.getTwoPointGrading().getTwoId().longValue());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_grading.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateGradingSameTest() throws Exception {
        // Given gradId (same) 3L -> 3L; free_point (same) 1L -> 1L
        schemeService.updateGrading(1L, 3L, 1L);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals(3L, foundScheme.getGrading().getGradingId().longValue());

        // test insertion into scheme_free_point table
        final SchemeFreePoint foundFree =
                (SchemeFreePoint) em.createQuery(FIND_GRADING_FREE)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertEquals(1L, foundFree.getFreePointGrading().getFreeId().longValue());
    }

    // Themes

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_themes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeThemeTest() throws Exception {
        // Given 2 themes, lets remove second
        schemeService.removeTheme(1L, 2L);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals(1, foundScheme.getThemes().size());
    }

    @Test(expected = RuntimeException.class)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_themes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeLastThemeTest() throws Exception {
        // Given 2 themes, lets remove second, and attempt to remove the last one
        schemeService.removeTheme(1L, 1L);
        // expect exception here
        schemeService.removeTheme(1L, 2L);
    }

    // Re-order

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void reOrderTest() throws Exception {
        // 1. Given 8 themes associated with a grading
        // 2. Reorder elements by putting 2d currentIndex element to the top and 5th currentIndex element to the bottom
        //    {1, 2, 3, 4, 5, 6, 7, 8} - > {3, 1, 2, 4, 5, 7, 8, 6}
        // 3. Make sure 8 of them are still present after manipulations and resulting lists are equal?
        // 4. Observe indexes re-built!
        final List<Long> edit = Arrays.asList(3L, 1L, 2L, 4L, 5L, 7L, 8L, 6L);
        schemeService.reOrderThemes(1L, edit);
        final List<SchemeTheme> schemeThemes = schemeRepository.findForThemesManipulationById(1L).getThemes();
        final List<Long> actual = schemeThemes.stream()
                .map(SchemeTheme::getSchemeThemeId)
                .collect(Collectors.toList());
        Assert.assertEquals(8, actual.size());
        Assert.assertEquals(edit, actual);
    }


    // Groups

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_groups.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addGroupTest() throws Exception {
        // Given 2 groups associated with Scheme, let's add third one (ID = 3)
        schemeService.addGroup(1L, 3L);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals(3, foundScheme.getGroups().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_groups.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeGroupTest() throws Exception {
        // Given 2 groups associated with Scheme, let's remove second one (ID = 2)
        schemeService.removeGroup(1L, 2L);
        final Scheme foundScheme =
                (Scheme) em.createQuery(FIND_WITH_COLLECTIONS)
                        .setParameter("schemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundScheme);
        Assert.assertEquals(1, foundScheme.getGroups().size());
    }



    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() throws Exception {
        schemeService.deleteById(1L);
        Optional<Scheme> scheme = schemeRepository.findById(1L);
        Assert.assertFalse(scheme.isPresent());
    }

}
