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
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.repository.CourseRepository;
import ua.edu.ratos.it.ActiveProfile;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTestIT {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByThemeIdTest() {
        Course course = courseRepository.findForSecurityById(1L);
        Assert.assertEquals(1L, course.getAccess().getAccessId().longValue());
        Assert.assertEquals(1L, course.getStaff().getStaffId().longValue());
        Assert.assertEquals(1L, course.getStaff().getDepartment().getDepId().longValue());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        List<Course> content = courseRepository.findAllByStaffId(1L, PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(8, content.size()); // 7+1
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        List<Course> content = courseRepository.findAllByStaffIdAndNameContains(1L, "edX", PageRequest.of(0, 100)).getContent();
        Assert.assertEquals(2, content.size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Course> content = courseRepository.findAllByDepartmentId(3L, PageRequest.of(0, 100));
        Assert.assertEquals(8, content.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameContainsTest() {
        Page<Course> content = courseRepository.findAllByDepartmentIdAndNameContains(3L, "LTI", PageRequest.of(0, 100));
        Assert.assertEquals(3, content.getContent().size());
    }
}
