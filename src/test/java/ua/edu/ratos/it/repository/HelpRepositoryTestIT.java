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
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.repository.HelpRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HelpRepositoryTestIT {

    @Autowired
    private HelpRepository helpRepository;

    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByStaffIdWithResourcesTest() {
        Assert.assertEquals(7, helpRepository.findAll().size());
        Page<Help> helps = helpRepository.findByStaffIdWithResources(1L, PageRequest.of(0, 20));
        Assert.assertEquals(3, helps.getContent().size());
        Assert.assertEquals(1, helps.getContent().get(0).getResources().size());
        Assert.assertEquals(1, helps.getContent().get(1).getResources().size());
        Assert.assertEquals(1, helps.getContent().get(2).getResources().size());
    }

    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByStaffIdAndFirstNameLettersWithResourcesTest() {
        Assert.assertEquals(7, helpRepository.findAll().size());
        Page<Help> helps = helpRepository.findByStaffIdAndFirstNameLettersWithResources(1L, "assist", PageRequest.of(0, 20));
        Assert.assertEquals(2, helps.getContent().size());
        Assert.assertEquals(1, helps.getContent().get(0).getResources().size());
        Assert.assertEquals(1, helps.getContent().get(1).getResources().size());
    }

    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentIdWithResourcesTest() {
        Assert.assertEquals(7, helpRepository.findAll().size());
        Page<Help> helps = helpRepository.findByDepartmentIdWithResources(1L, PageRequest.of(0, 20));
        Assert.assertEquals(3, helps.getContent().size());
        Assert.assertEquals(1, helps.getContent().get(0).getResources().size());
        Assert.assertEquals(1, helps.getContent().get(1).getResources().size());
        Assert.assertEquals(1, helps.getContent().get(2).getResources().size());
    }

    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentIdAndFirstNameLettersWithResourcesTest() {
        Assert.assertEquals(7, helpRepository.findAll().size());
        Page<Help> helps = helpRepository.findByDepartmentIdAndFirstNameLettersWithResources(1L, "assist", PageRequest.of(0, 20));
        Assert.assertEquals(2, helps.getContent().size());
        Assert.assertEquals(1, helps.getContent().get(0).getResources().size());
        Assert.assertEquals(1, helps.getContent().get(1).getResources().size());
    }

}
