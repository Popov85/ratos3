package ua.edu.ratos.dao.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.Course;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Tuple;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class CourseRepositoryTestIT {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private PersistenceUnitUtil persistenceUnitUtil;

    @Before
    public void setUp() {
        persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
    }

    //-------------------------------------------------SECURITY-------------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByIdTest() {
        Course course = courseRepository.findForSecurityById(1L);
        // Fetched Access, Staff, Department
        assertTrue("Access of Course is not loaded", persistenceUnitUtil.isLoaded(course, "access"));
        assertTrue("Staff of Course is not loaded", persistenceUnitUtil.isLoaded(course, "staff"));
        assertTrue("User of Staff is not loaded", persistenceUnitUtil.isLoaded(course.getStaff(), "user"));
        assertTrue("Department of Staff is not loaded", persistenceUnitUtil.isLoaded(course.getStaff(), "department"));
        assertThat("Course object is not as expected", course, allOf(
                hasProperty("courseId", equalTo(1L)),
                hasProperty("name", equalTo("Test LTI course #1")),
                hasProperty("created", is(notNullValue())),
                hasProperty("deleted", equalTo(false)))
        );
    }

    //----------------------------------------------INSTRUCTOR table---------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        Page<Course> page = courseRepository.findAllByStaffId(1L, PageRequest.of(0, 50));
        assertThat("Page of Courses is not as expected", page, allOf(
                hasProperty("content", hasSize(8)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(8L))));
        page.getContent()
                .forEach(c ->{
                    assertTrue("Access was not loaded", persistenceUnitUtil.isLoaded(c, "access"));
                    assertTrue("Staff was not loaded", persistenceUnitUtil.isLoaded(c, "staff"));
                    assertTrue("User of Staff was not loaded", persistenceUnitUtil.isLoaded(c.getStaff(), "user"));
                    assertTrue("Position of User of Staff was not loaded", persistenceUnitUtil.isLoaded(c.getStaff().getUser(), "position"));
                });
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        Page<Course> page = courseRepository.findAllByStaffIdAndNameLettersContains(1L, "edX", PageRequest.of(0, 100));
        assertThat("Page of Courses is not as expected", page, allOf(
                hasProperty("content", hasSize(2)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(2L))));
        page.getContent()
                .forEach(c ->{
                    assertTrue("Access was not loaded", persistenceUnitUtil.isLoaded(c, "access"));
                    assertTrue("Staff was not loaded", persistenceUnitUtil.isLoaded(c, "staff"));
                });
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Course> page = courseRepository.findAllByDepartmentId(3L, PageRequest.of(0, 100));
        assertThat("Page of Courses is not as expected", page, allOf(
                hasProperty("content", hasSize(8)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(8L))));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameContainsTest() {
        Page<Course> page = courseRepository.findAllByDepartmentIdAndNameLettersContains(3L, "LTI", PageRequest.of(0, 100));
        assertThat("Page of Courses is not as expected", page, allOf(
                hasProperty("content", hasSize(3)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(3L))));
    }

    //--------------------------------------------------DROPDOWN min----------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMinForDropDownByStaffIdTest() {
        Set<Course> all = courseRepository.findAllForDropDownByStaffId(1L);
        assertThat("Set of Courses is not of right size", all, hasSize(8));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllMinForDropDownByDepartmentIdTest() {
        Set<Course> all = courseRepository.findAllForDropDownByDepartmentId(3L);
        assertThat("Set of Courses is not of right size", all, hasSize(8));
    }

    //------------------------------------------------DROPDOWN slice----------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByDepartmentIdTest() {
        Slice<Course> slice = courseRepository.findAllForDropDownByDepartmentId(3L, PageRequest.of(0, 50));
        assertThat("Slice of Courses is not as expected", slice, allOf(
                hasProperty("number", equalTo(0)),
                hasProperty("size", equalTo(50)),
                hasProperty("numberOfElements", equalTo(8)),
                hasProperty("content", hasSize(8)),
                hasProperty("first", equalTo(true)),
                hasProperty("last", equalTo(true))
        ));
    }

    //--------------------------------------------------ADMIN-----------------------------------------------------------

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        Page<Course> page = courseRepository.findAll(PageRequest.of(0, 100));
        assertThat("Page of Courses is not as expected", page, allOf(
                hasProperty("content", hasSize(21)),
                hasProperty("totalPages", equalTo(1)),
                hasProperty("totalElements", equalTo(21L))));
    }

    //---------------------------------------------REPORT on content----------------------------------------------------
    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countCoursesByDepOfDepId() {
        Tuple coursesByDep = courseRepository.countCoursesByDepOfDepId(1L);
        assertThat("Org. name is not as expected", coursesByDep.get("org"), equalTo("University"));
        assertThat("Fac. name is not as expected", coursesByDep.get("fac"), equalTo("Faculty"));
        assertThat("Dep. name is not as expected", coursesByDep.get("dep"), equalTo("Department"));
        assertThat("Count of courses is not as expected", coursesByDep.get("count"), equalTo(8L));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countCoursesByDepOfFacId() {
        Set<Tuple> coursesByDeps = courseRepository.countCoursesByDepOfFacId(1L);
        assertThat("Count tuple of courses by dep is not of right size", coursesByDeps, hasSize(3));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countCoursesByDepOfFacIdNegative() {
        Set<Tuple> coursesByDeps = courseRepository.countCoursesByDepOfFacId(2L);
        assertThat("Count tuple of courses by dep is not empty", coursesByDeps, empty());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countCoursesByDepOfOrgId() {
        Set<Tuple> coursesByDeps = courseRepository.countCoursesByDepOfOrgId(1L);
        assertThat("Count tuple of courses by dep is not of right size", coursesByDeps, hasSize(3));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countCoursesByDepOfOrgIdNegative() {
        Set<Tuple> coursesByDeps = courseRepository.countCoursesByDepOfOrgId(2L);
        assertThat("Count tuple of courses by dep is not empty", coursesByDeps, empty());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countCoursesByDepOfRatos() {
        Set<Tuple> coursesByDeps = courseRepository.countCoursesByDepOfRatos();
        /*for (Tuple coursesByDep : coursesByDeps) {
            log.debug("Org = {}, Fac = {}, Dep = {}, count = {}", coursesByDep.get("org"), coursesByDep.get("fac"), coursesByDep.get("dep"), coursesByDep.get("count"));
        }*/
        assertThat("Count tuple of courses by dep is not of right size", coursesByDeps, hasSize(3));
    }

}
