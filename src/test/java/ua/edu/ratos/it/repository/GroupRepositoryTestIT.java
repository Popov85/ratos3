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
import ua.edu.ratos.dao.entity.Group;
import ua.edu.ratos.dao.repository.GroupRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class GroupRepositoryTestIT {

    @Autowired
    private GroupRepository groupRepository;

    //-------------------------------------------------ONE for edit-----------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/group_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        Group result = groupRepository.findOneForEdit(1L);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.getStudents().size());
    }

    //------------------------------------------------INSTRUCTOR table--------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/group_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        Page<Group> result = groupRepository.findAllByStaffId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(4, result.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/group_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Group> result = groupRepository.findAllByDepartmentId(2L, PageRequest.of(0, 50));
        Assert.assertEquals(4, result.getContent().size());
    }

    //-----------------------------------------------------Search-------------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/group_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameLettersContainsTest() {
        Page<Group> result = groupRepository.findAllByStaffIdAndNameLettersContains(1L, "19/20", PageRequest.of(0, 50));
        Assert.assertEquals(2, result.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/group_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        Page<Group> result = groupRepository.findAllByDepartmentIdAndNameLettersContains(2L, "1st", PageRequest.of(0, 50));
        Assert.assertEquals(2, result.getContent().size());
    }

    //-----------------------------------------------DROPDOWN slice-----------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/group_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByDepartmentIdTest() {
        Slice<Group> result = groupRepository.findAllForDropDownByDepartmentId(2L, PageRequest.of(0, 100));
        Assert.assertFalse(result.hasNext());
        Assert.assertEquals(4, result.getContent().size());
    }


    //----------------------------------------------------ADMIN---------------------------------------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/group_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllAdminTest() {
        Page<Group> result = groupRepository.findAllAdmin(PageRequest.of(0, 50));
        Assert.assertEquals(8, result.getContent().size());
    }
}
