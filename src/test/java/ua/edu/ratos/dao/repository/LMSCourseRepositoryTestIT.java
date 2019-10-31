package ua.edu.ratos.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.lms.LMSCourse;
import ua.edu.ratos.dao.repository.lms.LMSCourseRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LMSCourseRepositoryTestIT {

    @Autowired
    private LMSCourseRepository lmsCourseRepository;

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        Optional<LMSCourse> optional = lmsCourseRepository.findForEditById(1L);
        assertTrue("Course was not found with courseId = 1L", optional.isPresent());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByIdTest() {
        Optional<LMSCourse> optional = lmsCourseRepository.findForSecurityById(1L);
        assertTrue("Course was not found with courseId = 1L", optional.isPresent());
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        assertThat("Page of LMSCourse is not of size = 5",
                lmsCourseRepository.findAllByStaffId(1L, PageRequest.of(0, 100)).getContent(), hasSize(5));
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        assertThat("Page of LMSCourse is not of size = 5",
                lmsCourseRepository.findAllByDepartmentId(2L, PageRequest.of(0, 100)).getContent(), hasSize(5));
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        assertThat("Page of LMSCourse is not of size = 1",
                lmsCourseRepository.findAllByStaffIdAndNameLettersContains(1L, "edX", PageRequest.of(0, 100)).getContent(), hasSize(1));
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameContainsTest() {
        assertThat("Page of LMSCourse is not of size = 1",
                lmsCourseRepository.findAllByDepartmentIdAndNameLettersContains(2L, "lti", PageRequest.of(0, 100)).getContent(), hasSize(1));
    }

    //----------------------------------------------------ADMIN---------------------------------------------------------
    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllAdminTest() {
        assertThat("Page of LMSCourse is not of size = 10",
                lmsCourseRepository.findAll(PageRequest.of(0, 50)).getContent(), hasSize(10));
    }
}
