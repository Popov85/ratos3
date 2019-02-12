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
import ua.edu.ratos.dao.repository.SettingsRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SettingsRepositoryTestIT {

    @Autowired
    private SettingsRepository settingsRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        Assert.assertEquals(7, settingsRepository.findAll(PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllDefaultTest() {
        Assert.assertEquals(1, settingsRepository.findAllDefault().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        Assert.assertEquals(4, settingsRepository.findAllByStaffId(1L, PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameLettersContainsTest() {
        Assert.assertEquals(2, settingsRepository.findAllByStaffIdAndNameLettersContains(4L, "year", PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Assert.assertEquals(3, settingsRepository.findAllByDepartmentId(2L, PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        Assert.assertEquals(3, settingsRepository.findAllByDepartmentIdAndNameLettersContains(1L, "c", PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsNegativeOutcomeTest() {
        Assert.assertEquals(0, settingsRepository.findAllByDepartmentIdAndNameLettersContains(2L, "year6", PageRequest.of(0, 50)).getContent().size());
    }
}
