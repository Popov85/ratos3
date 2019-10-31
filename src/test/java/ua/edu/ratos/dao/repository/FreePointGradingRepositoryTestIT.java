package ua.edu.ratos.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FreePointGradingRepositoryTestIT {

    @Autowired
    private FreePointGradingRepository freePointGradingRepository;

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/free_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        Optional<FreePointGrading> optional = freePointGradingRepository.findOneForEdit(1L);
        assertTrue("FreePointGrading was not found with freeId = 1L", optional.isPresent());
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/free_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        assertThat("Page of FreePointGrading is not of size = 10",
                freePointGradingRepository.findAll(PageRequest.of(0, 50)).getContent(), hasSize(10));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/free_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllDefaultTest() {
        assertThat("Set of default FreePointGrading is not of size = 2",
                freePointGradingRepository.findAllDefault(), hasSize(2));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/free_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        assertThat("Slice of FreePointGrading is not of size = 4",
                freePointGradingRepository.findAllByStaffId(4L, PageRequest.of(0, 50)).getContent(), hasSize(4));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/free_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        assertThat("Slice of FreePointGrading is not of size = 4",
                freePointGradingRepository.findAllByDepartmentId(2L, PageRequest.of(0, 50)).getContent(), hasSize(4));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/free_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameLettersContainsTest() {
        assertThat("Slice of FreePointGrading is not of size = 2",
                freePointGradingRepository.findAllByStaffIdAndNameLettersContains(1L, "year", PageRequest.of(0, 50)).getContent(), hasSize(2));
    }

    @Test(timeout = 5000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/free_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        assertThat("Slice of FreePointGrading is not of size = 2",
                freePointGradingRepository.findAllByDepartmentIdAndNameLettersContains(2L, "Strict", PageRequest.of(0, 50)).getContent(), hasSize(2));
    }

}
