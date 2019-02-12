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
import ua.edu.ratos.dao.entity.lms.LMSCourse;
import ua.edu.ratos.dao.repository.lms.LMSCourseRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class LMSCourseRepositoryTestIT {

    @Autowired
    private LMSCourseRepository lmsCourseRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        LMSCourse course = lmsCourseRepository.findForSecurityById(1L);
        Assert.assertNotNull(course);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByIdTest() {
        LMSCourse course = lmsCourseRepository.findForSecurityById(1L);
        Assert.assertEquals(1L, course.getCourse().getAccess().getAccessId().longValue());
        Assert.assertEquals(1L, course.getCourse().getStaff().getStaffId().longValue());
        Assert.assertEquals(1L, course.getCourse().getStaff().getDepartment().getDepId().longValue());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        Page<LMSCourse> page = lmsCourseRepository.findAllByStaffId(1L, PageRequest.of(0, 100));
        Assert.assertEquals(5, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_" + ActiveProfile.NOW + ".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        Page<LMSCourse> page = lmsCourseRepository.findAllByStaffIdAndNameLettersContains(1L, "edX", PageRequest.of(0, 100));
        Assert.assertEquals(1, page.getContent().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<LMSCourse> page = lmsCourseRepository.findAllByDepartmentId(2L, PageRequest.of(0, 100));
        Assert.assertEquals(5, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameContainsTest() {
        Page<LMSCourse> page = lmsCourseRepository.findAllByDepartmentIdAndNameLettersContains(2L, "lti", PageRequest.of(0, 100));
        Assert.assertEquals(1, page.getContent().size());
    }
}
