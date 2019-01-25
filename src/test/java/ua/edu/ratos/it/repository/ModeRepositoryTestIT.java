package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.repository.ModeRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ModeRepositoryTestIT {

    @Autowired
    private ModeRepository modeRepository;


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        Assert.assertEquals(9, modeRepository.findAll(PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllDefaultTest() {
        Assert.assertEquals(1, modeRepository.findAllDefault().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByStaffIdTest() {
        Assert.assertEquals(4, modeRepository.findAllForTableByStaffId(4L, PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByStaffIdAndModeNameLettersContainsTest() {
        Assert.assertEquals(2, modeRepository.findAllForTableByStaffIdAndModeNameLettersContains(1L, "pre", PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByDepartmentIdTest() {
        Assert.assertEquals(4, modeRepository.findAllForTableByDepartmentId(2L, PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByDepartmentIdAndModeNameLettersContainsTest() {
        Assert.assertEquals(1, modeRepository.findAllForTableByDepartmentIdAndModeNameLettersContains(2L, "ing", PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByDepartmentIdAndModeNameLettersContainsNegativeOutcomeTest() {
        Assert.assertEquals(0, modeRepository.findAllForTableByDepartmentIdAndModeNameLettersContains(2L, "step", PageRequest.of(0, 50)).getContent().size());
    }

    //-------------------------------DROPDOWN search------------------------

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByStaffIdAndModeNameLettersContainsTest() {
        Assert.assertEquals(2, modeRepository.findAllForDropDownByStaffIdAndModeNameLettersContains(1L, "pre", PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByDepartmentIdAndModeNameLettersContainsTest() {
        Assert.assertEquals(1, modeRepository.findAllForDropDownByDepartmentIdAndModeNameLettersContains(2L, "ing", PageRequest.of(0, 50)).getContent().size());
    }
}
