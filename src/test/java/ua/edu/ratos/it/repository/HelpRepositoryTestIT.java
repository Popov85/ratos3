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
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.repository.HelpRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HelpRepositoryTestIT {

    @Autowired
    private HelpRepository helpRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        Page<Help> helps = helpRepository.findAllByStaffId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, helps.getContent().size());
        Assert.assertTrue(helps.getContent().get(0).getResource().isPresent());
        Assert.assertTrue(helps.getContent().get(1).getResource().isPresent());
        Assert.assertTrue(helps.getContent().get(2).getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndNameLettersContainsTest() {
        Page<Help> helps = helpRepository.findAllByStaffIdAndNameLettersContains(1L, "assist", PageRequest.of(0, 50));
        Assert.assertEquals(2, helps.getContent().size());
        Assert.assertTrue(helps.getContent().get(0).getResource().isPresent());
        Assert.assertTrue(helps.getContent().get(1).getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        Page<Help> helps = helpRepository.findAllByDepartmentId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, helps.getContent().size());
        Assert.assertTrue(helps.getContent().get(0).getResource().isPresent());
        Assert.assertTrue(helps.getContent().get(1).getResource().isPresent());
        Assert.assertTrue(helps.getContent().get(2).getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndNameLettersContainsTest() {
        Page<Help> helps = helpRepository.findAllByDepartmentIdAndNameLettersContains(1L, "assist", PageRequest.of(0, 50));
        Assert.assertEquals(2, helps.getContent().size());
        Assert.assertTrue(helps.getContent().get(0).getResource().isPresent());
        Assert.assertTrue(helps.getContent().get(1).getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        Page<Help> helps = helpRepository.findAll(PageRequest.of(0, 50));
        Assert.assertEquals(7, helps.getContent().size());
    }

}
