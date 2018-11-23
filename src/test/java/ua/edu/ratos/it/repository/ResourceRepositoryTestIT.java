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
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.dao.repository.ResourceRepository;
import ua.edu.ratos.it.ActiveProfile;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResourceRepositoryTestIT {

    @Autowired
    private ResourceRepository resourceRepository;


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/resource_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByStaffIdTest() {
        final List<Resource> all = resourceRepository.findAll();
        Assert.assertEquals(7, all.size());
        Page<Resource> resources = resourceRepository.findByStaffId(1L, PageRequest.of(0, 20));
        Assert.assertEquals(20, resources.getSize());
        Assert.assertEquals(2, resources.getContent().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/resource_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentIdTest() { final List<Resource> all = resourceRepository.findAll();
        Assert.assertEquals(7, all.size());
        final Page<Resource> resources = resourceRepository.findByDepartmentId(1L, PageRequest.of(0, 20));
        Assert.assertEquals(20, resources.getSize());
        Assert.assertEquals(2, resources.getContent().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/resource_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByStaffIdAndFirstLettersTest() {
        final List<Resource> all = resourceRepository.findAll();
        Assert.assertEquals(7, all.size());
        Page<Resource> resources = resourceRepository.findByStaffIdAndFirstLetters(1L, "Diagram", PageRequest.of(0, 20));
        Assert.assertEquals(0, resources.getContent().size());
        resources = resourceRepository.findByStaffIdAndFirstLetters(2L, "Image", PageRequest.of(0, 20));
        Assert.assertEquals(2, resources.getContent().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/resource_test_data_many.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentIdAndFirstLettersTest() {
        final List<Resource> all = resourceRepository.findAll();
        Assert.assertEquals(7, all.size());
        Page<Resource> resources = resourceRepository.findByDepartmentIdAndFirstLetters(1L, "Diagram", PageRequest.of(0, 20));
        Assert.assertEquals(0, resources.getContent().size());
        resources = resourceRepository.findByDepartmentIdAndFirstLetters(2L, "Image", PageRequest.of(0, 20));
        Assert.assertEquals(2, resources.getContent().size());
    }
}
