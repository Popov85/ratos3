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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ResourceRepositoryTestIT {

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/resource_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForUpdateTest() {
        Resource resource = resourceRepository.findOneForUpdate(1L);
        Assert.assertNotNull(resource);
        Assert.assertNotNull(resource.getStaff());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/resource_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        Page<Resource> resources = resourceRepository.findAll(PageRequest.of(0, 50));
        Assert.assertEquals(7, resources.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/resource_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByStaffIdTest() {
        Page<Resource> resources = resourceRepository.findByStaffId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(2, resources.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/resource_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentIdTest() {
        final Page<Resource> resources = resourceRepository.findByDepartmentId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(2, resources.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/resource_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByStaffIdAndDescriptionLettersContainsTest() {
        Page<Resource> resources = resourceRepository.findByStaffIdAndDescriptionLettersContains(1L, "Diagram", PageRequest.of(0, 20));
        Assert.assertEquals(0, resources.getContent().size());
        resources = resourceRepository.findByStaffIdAndDescriptionLettersContains(2L, "Image", PageRequest.of(0, 20));
        Assert.assertEquals(2, resources.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/resource_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentIdAndDescriptionLettersContainsTest() {
        Page<Resource> resources = resourceRepository.findByDepartmentIdAndDescriptionLettersContains(1L, "Diagram", PageRequest.of(0, 20));
        Assert.assertEquals(0, resources.getContent().size());
        resources = resourceRepository.findByDepartmentIdAndDescriptionLettersContains(2L, "Image", PageRequest.of(0, 20));
        Assert.assertEquals(2, resources.getContent().size());
    }
}
