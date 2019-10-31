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
public class ModeRepositoryTestIT {

    @Autowired
    private ModeRepository modeRepository;

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        assertTrue("Mode was not found for modeId = 1L",
                modeRepository.findOneForEdit(1L).isPresent());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllDefaultTest() {
        assertThat("Set of default Mode is not of size = 3",
                modeRepository.findAllDefault(), hasSize(3));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByStaffIdTest() {
        assertThat("Page of Mode is not of size = 4",
                modeRepository.findAllForTableByStaffId(4L, PageRequest.of(0, 50)).getContent(), hasSize(4));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByStaffIdAndModeNameLettersContainsTest() {
        assertThat("Page of Mode is not of size = 2",
                modeRepository.findAllForTableByStaffIdAndModeNameLettersContains(1L, "pre", PageRequest.of(0, 50)).getContent(), hasSize(2));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByDepartmentIdTest() {
        assertThat("Page of Mode is not of size = 4",
                modeRepository.findAllForTableByDepartmentId(2L, PageRequest.of(0, 50)).getContent(), hasSize(4));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByDepartmentIdAndModeNameLettersContainsTest() {
        assertThat("Page of Mode is not of size = 1",
                modeRepository.findAllForTableByDepartmentIdAndModeNameLettersContains(2L, "ing", PageRequest.of(0, 50)).getContent(), hasSize(1));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForTableByDepartmentIdAndModeNameLettersContainsNegativeOutcomeTest() {
        assertThat("Page of Mode is not empty",
                modeRepository.findAllForTableByDepartmentIdAndModeNameLettersContains(2L, "step", PageRequest.of(0, 50)).getContent(), empty());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByStaffIdAndModeNameLettersContainsTest() {
        assertThat("Page of Mode is not of size = 2",
                modeRepository.findAllForDropDownByStaffIdAndModeNameLettersContains(1L, "pre", PageRequest.of(0, 50)).getContent(), hasSize(2));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllForDropDownByDepartmentIdAndModeNameLettersContainsTest() {
        assertThat("Page of Mode is not of size = 1",
                modeRepository.findAllForDropDownByDepartmentIdAndModeNameLettersContains(2L, "ing", PageRequest.of(0, 50)).getContent(), hasSize(1));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/mode_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        assertThat("Page of Mode is not of size = 9", modeRepository.findAll(PageRequest.of(0, 50)).getContent(), hasSize(9));
    }
}
