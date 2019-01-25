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
import ua.edu.ratos.dao.repository.TwoPointGradingRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TwoPointGradingRepositoryTestIT {

    @Autowired
    private TwoPointGradingRepository TwoPointGradingRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/two_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        Assert.assertEquals(9, TwoPointGradingRepository.findAll(PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/two_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllDefaultTest() {
        Assert.assertEquals(1, TwoPointGradingRepository.findAllDefault().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/two_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        Assert.assertEquals(4, TwoPointGradingRepository.findAllByStaffId(4L, PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/two_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Assert.assertEquals(4, TwoPointGradingRepository.findAllByDepartmentId(2L, PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/two_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameLettersContainsTest() {
        Assert.assertEquals(2, TwoPointGradingRepository.findAllByStaffIdAndNameLettersContains(1L, "year", PageRequest.of(0, 50)).getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/two_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        Assert.assertEquals(2, TwoPointGradingRepository.findAllByDepartmentIdAndNameLettersContains(2L, "Strict", PageRequest.of(0, 50)).getContent().size());
    }


}
