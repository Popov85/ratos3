package ua.edu.ratos.dao.repository;

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

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
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
                //hasProperty("hasContent", equalTo(true)),
                hasProperty("first", equalTo(true)),
                hasProperty("last", equalTo(true))
                //hasProperty("hasNext", equalTo(false)),
                //hasProperty("hasPrevious", equalTo(false))
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
}
