package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Course;
import ua.edu.ratos.dao.repository.CourseRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTestIT {

    @Autowired
    private CourseRepository courseRepository;

    //-------------------------------------------------SECURITY-------------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findForSecurityByIdTest() {
        Course course = courseRepository.findForSecurityById(1L);
        System.out.println("course = "+course);
        Assert.assertEquals(1L, course.getAccess().getAccessId().longValue());
        Assert.assertEquals(1L, course.getStaff().getStaffId().longValue());
        Assert.assertEquals(1L, course.getStaff().getDepartment().getDepId().longValue());
    }

    //----------------------------------------------INSTRUCTOR table---------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        Page<Course> content = courseRepository.findAllByStaffId(1L, PageRequest.of(0, 100));
        Assert.assertEquals(8, content.getContent().size()); // 7+1
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameContainsTest() {
        Page<Course> content = courseRepository.findAllByStaffIdAndNameLettersContains(1L, "edX", PageRequest.of(0, 100));
        Assert.assertEquals(2, content.getContent().size());
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
        Page<Course> content = courseRepository.findAllByDepartmentIdAndNameLettersContains(3L, "LTI", PageRequest.of(0, 100));
        Assert.assertEquals(3, content.getContent().size());
    }

    //----------------------------------------------DROPDOWN slice----------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByDepartmentIdTest() {
        Slice<Course> content = courseRepository.findAllForDropDownByDepartmentId(3L, PageRequest.of(0, 100));
        Assert.assertFalse(content.hasNext());
        Assert.assertEquals(8, content.getContent().size());
    }

    //------------------------------------------------ADMIN-----------------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/course_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        Page<Course> content = courseRepository.findAll(PageRequest.of(0, 100));
        Assert.assertEquals(1, content.getTotalPages());
        Assert.assertEquals(21, content.getTotalElements());
        Assert.assertEquals(21, content.getContent().size());
    }
}
