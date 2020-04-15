package ua.edu.ratos.service._it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.grading.SchemeFourPoint;
import ua.edu.ratos.dao.entity.grading.SchemeFreePoint;
import ua.edu.ratos.dao.entity.grading.SchemeTwoPoint;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.dto.in.SchemeInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchemeServiceTestIT {

    private static final String JSON_NEW = "classpath:json/scheme_in_dto_new.json";
    private static final String FIND_WITH_COLLECTIONS = "select s from Scheme s left join fetch s.groups left join fetch s.themes t left join fetch t.settings where s.schemeId=:schemeId";
    private static final String FIND_GRADING_FOUR = "select s from SchemeFourPoint s join fetch s.fourPointGrading where s.schemeId=:schemeId";
    private static final String FIND_GRADING_FREE = "select s from SchemeFreePoint s join fetch s.freePointGrading where s.schemeId=:schemeId";
    private static final String FIND_GRADING_TWO = "select s from SchemeTwoPoint s join fetch s.twoPointGrading where s.schemeId=:schemeId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        SchemeInDto dto = objectMapper.readValue(json, SchemeInDto.class);
        // Actual test begins
        schemeService.save(dto);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Saved Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("groups", hasSize(3)),
                hasProperty("themes", hasSize(3))
        ));
        // Verify insertion into scheme_four_point table
        final SchemeFourPoint foundFour = (SchemeFourPoint) em.createQuery(FIND_GRADING_FOUR).setParameter("schemeId", 1L).getSingleResult();
        assertNotNull("SchemeFourPoint is not found", foundFour);
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateNameTest() {
        // Update only name
        schemeService.updateName(1L, "Updated name");
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Updated name"))
        ));
    }

    /*@Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateStrategyTest() {
        // Update only strategy
        schemeService.updateStrategy(1L, 2L);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("strategy", hasProperty("strId", equalTo(2L)) )
        ));
    }*/

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateIsActiveTest() {
        // Deactivate
        schemeService.updateIsActive(1L, false);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("active", equalTo(false))
        ));
    }

    // Gradings
    /*@Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_grading.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateGradingNewDetailsTest() {
        // Given gradId (same) 3L -> 3L; free_point 1L -> 2L
        schemeService.updateGrading(1L, 3L, 2L);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("grading", hasProperty("gradingId", equalTo(3L)) )
        ));
        // Verify insertion into scheme_free_point table
        final SchemeFreePoint free = (SchemeFreePoint) em.createQuery(FIND_GRADING_FREE).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated SchemeFreePoint object is not as expected", free, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("freePointGrading", hasProperty("freeId", equalTo(2L)) )
        ));
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_grading.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateGradingNewTypeTest() {
        // Given gradId (new) 3L -> 2L; free_point 1L -> two_point 1L
        schemeService.updateGrading(1L, 2L, 1L);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("grading", hasProperty("gradingId", equalTo(2L)) )
        ));
        // Verify insertion into scheme_two_point table
        final SchemeTwoPoint two = (SchemeTwoPoint) em.createQuery(FIND_GRADING_TWO).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated SchemeTwoPoint object is not as expected", two, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("twoPointGrading", hasProperty("twoId", equalTo(1L)) )
        ));
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_grading.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateGradingSameTest() {
        // Given gradId (same) 3L -> 3L; free_point (same) 1L -> 1L
        schemeService.updateGrading(1L, 3L, 1L);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("grading", hasProperty("gradingId", equalTo(3L)) )
        ));
        // Verify insertion into scheme_free_point table
        final SchemeFreePoint free = (SchemeFreePoint) em.createQuery(FIND_GRADING_FREE).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated SchemeFreePoint object is not as expected", free, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("freePointGrading", hasProperty("freeId", equalTo(1L)) )
        ));
    }

    // Themes
    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_themes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeThemeTest() {
        // Given 2 themes, lets remove second
        schemeService.removeTheme(1L, 2L);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("themes", hasSize(1))
        ));
    }

    @Test(timeout = 10000, expected = RuntimeException.class)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_themes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeLastThemeExceptionTest() {
        // Given 2 themes, lets remove second, and attempt to remove the last one
        schemeService.removeTheme(1L, 1L);
        // expect exception here
        schemeService.removeTheme(1L, 2L);
    }

    // Re-order
    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_theme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void reOrderThemesTest() {
        // 1. Given 8 themes associated with a grading
        // 2. Reorder elements by putting 2d currentIndex element to the top and 5th currentIndex element to the bottom
        //    {1, 2, 3, 4, 5, 6, 7, 8} - > {3, 1, 2, 4, 5, 7, 8, 6}
        // 3. Make sure 8 of them are still present after manipulations and resulting lists are equal?
        // 4. Observe indexes re-built!
        final List<Long> expectedOrder = Arrays.asList(3L, 1L, 2L, 4L, 5L, 7L, 8L, 6L);
        schemeService.reOrderThemes(1L, expectedOrder );
        final List<SchemeTheme> schemeThemes = schemeRepository.findForThemesManipulationById(1L).get().getThemes();
        final List<Long> actualNewOrder = schemeThemes.stream().map(SchemeTheme::getSchemeThemeId).collect(Collectors.toList());
        assertThat("Re-ordered list of themes is not as expected", expectedOrder, equalTo(actualNewOrder));
    }

    // Groups
    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_groups.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addGroupTest() {
        // Given 2 groups associated with Scheme, let's add third one (ID = 3)
        schemeService.addGroup(1L, 3L);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("groups", hasSize(3))
        ));
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_groups.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeGroupTest() {
        // Given 2 groups associated with Scheme, let's remove second one (ID = 2)
        schemeService.removeGroup(1L, 2L);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("groups", hasSize(1))
        ));
    }

     */

}
