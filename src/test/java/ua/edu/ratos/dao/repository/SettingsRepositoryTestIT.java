package ua.edu.ratos.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.ActiveProfile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SettingsRepositoryTestIT {

    @Autowired
    private SettingsRepository settingsRepository;

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
       assertTrue("Settings is not found", settingsRepository.findOneForEdit(1L).isPresent());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllDefaultTest() {
        assertThat("Set of default Settings is not of size = 1", settingsRepository.findAllDefault(), hasSize(1));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        assertThat("Page of Settings is not of size = 4",
                settingsRepository.findAllByStaffId(1L, PageRequest.of(0, 50)).getContent(), hasSize(4));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        assertThat("Page of Settings is not of size = 3",
                settingsRepository.findAllByDepartmentId(2L, PageRequest.of(0, 50)).getContent(), hasSize(3));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameLettersContainsTest() {
        assertThat("Page of Settings is not of size = 2",
                settingsRepository.findAllByStaffIdAndNameLettersContains(4L, "year", PageRequest.of(0, 50)).getContent(), hasSize(2));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        assertThat("Page of Settings is not of size = 3",
                settingsRepository.findAllByDepartmentIdAndNameLettersContains(1L, "c", PageRequest.of(0, 50)).getContent(), hasSize(3));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsNegativeOutcomeTest() {
        assertThat("Page of Settings is not empty",
                settingsRepository.findAllByDepartmentIdAndNameLettersContains(2L, "year6", PageRequest.of(0, 50)).getContent(), empty());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllAdminTest() {
        assertThat("Page of Settings is not of size = 7",
                settingsRepository.findAllAdmin(PageRequest.of(0, 50)).getContent(), hasSize(7));
    }

}
