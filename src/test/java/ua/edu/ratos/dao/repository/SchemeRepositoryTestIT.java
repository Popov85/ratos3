package ua.edu.ratos.dao.repository;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.Scheme;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Tuple;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SchemeRepositoryTestIT {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private PersistenceUnitUtil persistenceUnitUtil;

    @Before
    public void setUp() {
        persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_groups_themes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForEditByIdTest() {
        Optional<Scheme> optional = schemeRepository.findForEditById(1L);
        assertTrue("Scheme is not found", optional.isPresent());
        assertTrue("Mode of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "mode"));
        assertTrue("Strategy of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "strategy"));
        assertTrue("Grading of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "grading"));
        assertTrue("Options of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "options"));
        assertTrue("Access of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "access"));
        assertTrue("Themes of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "themes"));
        assertTrue("Groups of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "groups"));
    }

    //------------------------------------------------------SECURITY----------------------------------------------------
    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByIdTest() {
        Optional<Scheme> optional = schemeRepository.findForSecurityById(1L);
        assertTrue("Scheme is not found", optional.isPresent());
        assertTrue("Access of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "access"));
        assertTrue("Staff of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "staff"));
        assertTrue("User of Staff of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get().getStaff(), "user"));
    }

    //-------------------------------------------------------SESSION----------------------------------------------------
    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_session.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSessionByIdTest() {
        Optional<Scheme> optional = schemeRepository.findForSessionById(1L);
        assertTrue("Scheme is not found", optional.isPresent());
        assertTrue("Mode of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "mode"));
        assertTrue("Strategy of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "strategy"));
        assertTrue("Grading of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "grading"));
        assertTrue("Options of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "options"));
        assertTrue("Themes of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "themes"));
        assertTrue("Groups of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "groups"));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_session.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForInfoByIdTest() {
        Optional<Scheme> optional = schemeRepository.findForInfoById(1L);
        assertTrue("Scheme is not found", optional.isPresent());
        assertTrue("Mode of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "mode"));
        assertTrue("Strategy of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "strategy"));
        assertTrue("Grading of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "grading"));
        assertTrue("Options of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "options"));
        assertTrue("Themes of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "themes"));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_session.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForThemesManipulationByIdTest() {
        Optional<Scheme> optional = schemeRepository.findForThemesManipulationById(1L);
        assertTrue("Scheme is not found", optional.isPresent());
        assertTrue("Themes of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "themes"));
        assertTrue("Settings of Themes of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get().getThemes(), "settings"));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one_session.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForGradingByIdTest() {
        Optional<Scheme> optional = schemeRepository.findForGradingById(1L);
        assertTrue("Scheme is not found", optional.isPresent());
        assertTrue("Grading of Scheme is not loaded", persistenceUnitUtil.isLoaded(optional.get(), "grading"));
    }


    //-------------------------------------------------------CACHE------------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_with_themes_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findLargeForCachedSessionTest() {
        assertThat("Slice of Scheme is not of size = 2",
                schemeRepository.findLargeForCachedSession(PageRequest.of(0, 10)).getContent(), hasSize(2));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_with_themes_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findCoursesSchemesForCachedSessionTest() {
        assertThat("Slice of Scheme is not of size = 2",
                schemeRepository.findCoursesSchemesForCachedSession(2L, PageRequest.of(0, 10)).getContent(), hasSize(2));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_with_themes_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findDepartmentSchemesForCachedSessionTest() {
        assertThat("Slice of Scheme is not of size = 2",
                schemeRepository.findDepartmentSchemesForCachedSession(2L, PageRequest.of(0, 10)).getContent(), hasSize(2));
    }


    //--------------------------------------------------INSTRUCTOR table----------------------------------------------
    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        assertThat("Page of Scheme is not of size = 10",
                schemeRepository.findAllByStaffId(1L, PageRequest.of(0, 100)).getContent(), hasSize(10));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        assertThat("Page of Scheme is not of size = 4",
                schemeRepository.findAllByStaffIdAndNameLettersContains(1L, "trainee", PageRequest.of(0, 100)).getContent(), hasSize(4));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        assertThat("Page of Scheme is not of size = 5",
                schemeRepository.findAllByDepartmentId(3L, PageRequest.of(0, 100)).getContent(), hasSize(5));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameContainsTest() {
        assertThat("Page of Scheme is not of size = 3",
                schemeRepository.findAllByDepartmentIdAndNameContains(2L, "exam", PageRequest.of(0, 100)).getContent(), hasSize(3));
    }

    //-------------------------------------------------DROPDOWN slice-------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByDepartmentIdTest() {
        assertThat("Slice of Scheme is not of size = 5",
                schemeRepository.findAllForDropDownByDepartmentId(3L, PageRequest.of(0, 100)).getContent(), hasSize(5));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByCourseIdTest() {
        assertThat("Slice of Scheme is not of size = 20",
                schemeRepository.findAllForDropDownByCourseId(1L, PageRequest.of(0, 100)).getContent(), hasSize(20));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByCourseIdMultipleSlicesTest() {
        assertThat("Slice of Scheme is not of size = 10",
                schemeRepository.findAllForDropDownByCourseId(1L, PageRequest.of(0, 10)).getContent(), hasSize(10));
    }


    //---------------------------------------------------ADMIN--------------------------------------------------------
    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        assertThat("Page of Scheme is not of size = 20",
                schemeRepository.findAll(PageRequest.of(0, 100)).getContent(), hasSize(20));
    }

    //---------------------------------------------REPORT on content----------------------------------------------------
    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countSchemesByDepOfDepId() {
        Tuple schemesByDep = schemeRepository.countSchemesByDepOfDepId(1L);
        MatcherAssert.assertThat("Org. name is not as expected", schemesByDep.get("org"), equalTo("University"));
        MatcherAssert.assertThat("Fac. name is not as expected", schemesByDep.get("fac"), equalTo("Faculty"));
        MatcherAssert.assertThat("Dep. name is not as expected", schemesByDep.get("dep"), equalTo("Department"));
        MatcherAssert.assertThat("Count of schemes is not as expected", schemesByDep.get("count"), equalTo(10L));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countSchemesByDepOfFacId() {
        Set<Tuple> schemesByDep = schemeRepository.countSchemesByDepOfFacId(1L);
        MatcherAssert.assertThat("Count tuple of schemes by dep is not of right size", schemesByDep, hasSize(3));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countSchemesByDepOfFacIdNegative() {
        Set<Tuple> schemesByDep = schemeRepository.countSchemesByDepOfFacId(2L);
        MatcherAssert.assertThat("Count tuple of schemes by dep is not empty", schemesByDep, empty());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countSchemesByDepOfOrgId() {
        Set<Tuple> schemesByDep = schemeRepository.countSchemesByDepOfOrgId(1L);
        MatcherAssert.assertThat("Count tuple of schemes by dep is not of right size", schemesByDep, hasSize(3));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countSchemesByDepOfOrgIdNegative() {
        Set<Tuple> schemesByDep = schemeRepository.countSchemesByDepOfOrgId(2L);
        MatcherAssert.assertThat("Count tuple of schemes by dep is not empty", schemesByDep, empty());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countSchemesByDepOfRatos() {
        Set<Tuple> schemesByDep = schemeRepository.countSchemesByDepOfRatos();
        MatcherAssert.assertThat("Count tuple of schemes by dep is not of right size", schemesByDep, hasSize(3));
    }
}
