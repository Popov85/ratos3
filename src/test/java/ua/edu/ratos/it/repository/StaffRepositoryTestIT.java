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
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.repository.StaffRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class StaffRepositoryTestIT {

    @Autowired
    private StaffRepository staffRepository;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForAuthenticationTest() {
        Staff staff = staffRepository.findByIdForAuthentication("alexei.portnov@example.com");
        Assert.assertNotNull(staff);
    }

    //----------------------------------------------------------------------------------------------------------------

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        Staff staff = staffRepository.findOneForEdit(1L);
        Assert.assertNotNull(staff);
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/staff_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Staff> page = staffRepository.findAllByDepartmentId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(4, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/staff_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        // Given 2 records satisfied the request: one from name, second from surname
        Page<Staff> page = staffRepository.findAllByDepartmentIdAndNameLettersContains(1L, "mm", PageRequest.of(0, 50));
        Assert.assertEquals(2, page.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/staff_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsNegativeTest() {
        Page<Staff> page = staffRepository.findAllByDepartmentIdAndNameLettersContains(1L, "ss", PageRequest.of(0, 50));
        Assert.assertTrue(page.getContent().isEmpty());
    }
}
