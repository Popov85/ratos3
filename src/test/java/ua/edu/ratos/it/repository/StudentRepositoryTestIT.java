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
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.repository.StudentRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTestIT {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForAuthenticationTest() {
        Student stud = studentRepository.findByIdForAuthentication("maria.medvedeva@example.com");
        Assert.assertNotNull(stud);
    }

    //-------------------------------------------------------------------------------------------------------------------


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        Student stud = studentRepository.findOneForEdit(2L);
        Assert.assertNotNull(stud);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/student_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrgIdTest() {
        Page<Student> page = studentRepository.findAllByOrgId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(4, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/student_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrgIdAndSurnameLettersContainsTest() {
        Slice<Student> page = studentRepository.findAllByOrgIdAndNameLettersContains(2L, "son", PageRequest.of(0, 50));
        Assert.assertEquals(2, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/student_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrgIdAndEmailLettersContainsTest() {
        Slice<Student> page = studentRepository.findAllByOrgIdAndNameLettersContains(2L, "com", PageRequest.of(0, 50));
        Assert.assertEquals(4, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/student_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrgIdAndNameLettersContainsNegativeTest() {
        Slice<Student> page = studentRepository.findAllByOrgIdAndNameLettersContains(2L, "fr", PageRequest.of(0, 50));
        Assert.assertTrue(page.getContent().isEmpty());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/student_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllAdminTest() {
        Page<Student> page = studentRepository.findAllAdmin(PageRequest.of(0, 50));
        Assert.assertEquals(8, page.getContent().size());
    }
}
