package ua.edu.ratos.integration_test.service;

import org.hibernate.Hibernate;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.repository.HelpRepository;
import ua.edu.ratos.domain.repository.QuestionRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.domain.repository.StaffRepository;
import ua.edu.ratos.service.HelpService;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HelpServiceTestIT {

    public static final String HELP_NAME = "Java doc URL on Cache interface";
    public static final String HELP_CONTENT = "Refer to https://docs.oracle.com/javaee/7/api/javax/persistence/Cache.html";
    public static final String RESOURCE_LINK = "https://image.slidesharecdn.com/slide01.jpg";
    public static final String RESOURCE_DESCRIPTION = "Caching schema";

    @Autowired
    private HelpService helpService;

    @Autowired
    private HelpRepository helpRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ResourceRepository resourceRepository;


    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWrongTest() {
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        helpService.save(help, null, null);
    }


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveSimpleTest() {
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        final Help savedHelp = helpService.save(help, 1L, 1L);
        Assert.assertTrue(helpRepository.findById(1L).isPresent());
    }


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWithResourceTest() {
        Resource resource = new Resource(RESOURCE_LINK, RESOURCE_DESCRIPTION);
        resource.setStaff(staffRepository.getOne(1L));
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        help.setResources(new HashSet<>(Arrays.asList(resource)));
        final Help savedHelp = helpService.save(help, 1L, 1L);
        final Help foundHelp = helpRepository.findByIdWithResources(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundHelp.getResources()));
        Assert.assertEquals(1, foundHelp.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        //1. Insert
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        help.setQuestion(questionRepository.getOne(1L));
        help.setStaff(staffRepository.getOne(1L));
        final Help savedHelp = helpRepository.save(help);

        // 2. Update
        savedHelp.setName("Cache interface");
        savedHelp.setHelp("Interface used to interact with the second-level cache");
        final Help updatedHelp = helpService.update(savedHelp);

        //3. Find and compare
        final Optional<Help> foundHelp = helpRepository.findById(1L);
        Assert.assertTrue(foundHelp.isPresent());
        Assert.assertEquals("Cache interface", foundHelp.get().getName());
        Assert.assertEquals("Interface used to interact with the second-level cache", foundHelp.get().getHelp());
        Assert.assertNotNull(foundHelp.get().getQuestion());
        Assert.assertNotNull(foundHelp.get().getStaff());
    }


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addResourceTest() {
        // 1. Insert with a resource
        Resource resource = new Resource(RESOURCE_LINK, RESOURCE_DESCRIPTION);
        resource.setStaff(staffRepository.getOne(1L));
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        help.setQuestion(questionRepository.getOne(1L));
        help.setStaff(staffRepository.getOne(1L));
        help.setResources(new HashSet<>(Arrays.asList(resource)));
        final Help savedHelp = helpRepository.save(help);

        // 2. Add 2-d resource
        Resource resource2 = new Resource("Caching schema2",
                "https://image.slidesharecdn.com/slide02.jpg");
        resource2.setStaff(staffRepository.getOne(1L));
        helpService.addResource(resource2, 1L);

        // 3. Find with resources and compare
        final Help foundHelp = helpRepository.findByIdWithResources(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundHelp.getResources()));
        Assert.assertEquals(2, foundHelp.getResources().size());
    }

    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addResourceExistingTest() {
        // 1. Insert with a resource
        Resource resource = new Resource(RESOURCE_LINK, RESOURCE_DESCRIPTION);
        resource.setStaff(staffRepository.getOne(1L));
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        help.setQuestion(questionRepository.getOne(1L));
        help.setStaff(staffRepository.getOne(1L));
        help.setResources(new HashSet<>(Arrays.asList(resource)));
        final Help savedHelp = helpRepository.save(help);

        // 2. Add 2-d resource
        Resource resource2 = new Resource("Caching schema2",
                "https://image.slidesharecdn.com/slide02.jpg");
        resource2.setStaff(staffRepository.getOne(1L));
        Resource savedResource = resourceRepository.save(resource2);
        helpService.addResource(savedResource.getResourceId(), 1L);

        // 3. Find with resources and compare
        final Help foundHelp = helpRepository.findByIdWithResources(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundHelp.getResources()));
        Assert.assertEquals(2, foundHelp.getResources().size());
    }

    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteResourceTest() {
        // 1. Insert with a resource
        Resource resource = new Resource(RESOURCE_LINK, RESOURCE_DESCRIPTION);
        resource.setStaff(staffRepository.getOne(1L));
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        help.setQuestion(questionRepository.getOne(1L));
        help.setStaff(staffRepository.getOne(1L));
        help.setResources(new HashSet<>(Arrays.asList(resource)));
        final Help savedHelp = helpRepository.save(help);

        // 2. Delete this single resource
        helpService.deleteResource(resource.getResourceId(), 1L, false);

        // 3. Find with resources and compare
        final Help foundHelp = helpRepository.findByIdWithResources(1L);
        Assert.assertTrue(Hibernate.isInitialized(foundHelp.getResources()));
        Assert.assertTrue(foundHelp.getResources().isEmpty());
    }

    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() {
        Help help = new Help(HELP_NAME, HELP_CONTENT);
        help.setQuestion(questionRepository.getOne(1L));
        help.setStaff(staffRepository.getOne(1L));
        final Help savedHelp = helpRepository.save(help);
        Assert.assertTrue(helpRepository.findById(1L).isPresent());
        helpService.deleteById(savedHelp.getHelpId());
        Assert.assertFalse(helpRepository.findById(1L).isPresent());
    }

}
