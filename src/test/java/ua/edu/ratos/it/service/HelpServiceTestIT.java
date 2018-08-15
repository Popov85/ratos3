package ua.edu.ratos.it.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.domain.repository.HelpRepository;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.entity.HelpInDto;
import javax.persistence.EntityManager;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HelpServiceTestIT {

    public static final String HELP_NAME = "Java doc URL on Cache interface";
    public static final String HELP_TEXT = "Refer to https://docs.oracle.com/javaee/7/api/javax/persistence/Cache.html";
    public static final String HELP_NAME_UPD = "Java documentation on Cache interface";
    public static final String HELP_TEXT_UPD = "Refer to https://docs.oracle.com";

    @Autowired
    private HelpService helpService;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private EntityManager em;


    @Test(expected = Exception.class)
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWrongTest() {
        HelpInDto dto = new HelpInDto(null, null, null, 0, 0);
        helpService.save(dto);
    }


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveSimpleTest() {
        HelpInDto dto = new HelpInDto(null, HELP_NAME, HELP_TEXT, 1L, 0);
        helpService.save(dto);
        final Help foundHelp = helpRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NAME, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT, foundHelp.getHelp());
        Assert.assertEquals(0, foundHelp.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveWithResourceTest() {
        HelpInDto dto = new HelpInDto(null, HELP_NAME, HELP_TEXT, 1L, 1);
        helpService.save(dto);
        final Help foundHelp = helpRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NAME, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT, foundHelp.getHelp());
        Assert.assertEquals(1, foundHelp.getResources().size());
    }


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() {
        //1. Insert
       insert();

        // 2. Update {name, text and resourcesId = 2}
        HelpInDto dto = new HelpInDto(1L, HELP_NAME_UPD, HELP_TEXT_UPD, 1L, 2);
        helpService.update(dto);

        //3. Find and compare
        final Help foundHelp = helpRepository.findByIdWithResources(1L);
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NAME_UPD, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT_UPD, foundHelp.getHelp());
        Assert.assertEquals(1, foundHelp.getResources().size());
        Assert.assertTrue(foundHelp.getResources().contains(em.find(Resource.class, 2L)));
    }

    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() {
        insert();
        Assert.assertTrue(helpRepository.findById(1L).isPresent());
        helpService.deleteById(1L);
        Assert.assertFalse(helpRepository.findById(1L).isPresent());
    }


    private Help insert() {
        Help help = new Help();
        help.setName(HELP_NAME);
        help.setHelp(HELP_TEXT);
        help.setStaff(em.getReference(Staff.class, 1L));
        help.setResources(new HashSet<>(Arrays.asList(em.find(Resource.class, 1L))));
        return helpRepository.save(help);
    }
}
