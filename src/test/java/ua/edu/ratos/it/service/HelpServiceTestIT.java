package ua.edu.ratos.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.entity.HelpInDto;
import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HelpServiceTestIT {

    public static final String JSON_NEW = "classpath:json/help_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/help_in_dto_upd.json";
    public static final String HELP_NEW = "javax.persistence";
    public static final String HELP_TEXT = "Please, refer to https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html";
    public static final String HELP_UPD = "JPA";
    public static final String HELP_TEXT_UPD = "Please, refer to https://javaee.github.io/javaee-spec/javadocs";
    public static final String RESOURCE_NAME = "Schema#1";
    public static final String RESOURCE_LINK = "https://image.slidesharecdn.com/schema01.jpg";
    public static final String RESOURCE_NAME_UPD = "Schema#2";
    public static final String RESOURCE_LINK_UPD = "https://image.slidesharecdn.com/schema02.jpg";

    public static final String FIND = "select h from Help h left join fetch h.resources where h.helpId=:helpId";

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HelpService helpService;


    @Test
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        HelpInDto dto = objectMapper.readValue(json, HelpInDto.class);
        helpService.save(dto);
        final Help foundHelp =
            (Help) em.createQuery(FIND)
                .setParameter("helpId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NEW, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT, foundHelp.getHelp());
        Assert.assertEquals(1, foundHelp.getResources().size());
        Assert.assertTrue(foundHelp.getResources().contains(new Resource(RESOURCE_LINK, RESOURCE_NAME)));
    }

    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        HelpInDto dto = objectMapper.readValue(json, HelpInDto.class);
        helpService.update(dto);
        final Help foundHelp =
            (Help) em.createQuery(FIND)
                .setParameter("helpId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_UPD, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT_UPD, foundHelp.getHelp());
        Assert.assertEquals(1, foundHelp.getResources().size());
        Assert.assertTrue(foundHelp.getResources().contains(new Resource(RESOURCE_LINK_UPD, RESOURCE_NAME_UPD)));
    }

    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByStaffTest() {
        List<Help> helps = helpService.findByStaff(1L);
        Assert.assertFalse(helps.isEmpty());
        Assert.assertEquals(3, helps.size());
        helps = helpService.findByStaff(2L);
        Assert.assertFalse(helps.isEmpty());
        Assert.assertEquals(4, helps.size());
    }

    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByDepartmentTest() {
        List<Help> helps = helpService.findByDepartment(1L);
        Assert.assertFalse(helps.isEmpty());
        Assert.assertEquals(3, helps.size());
        helps = helpService.findByDepartment(2L);
        Assert.assertFalse(helps.isEmpty());
        Assert.assertEquals(4, helps.size());
    }


    @Test
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() {
        Assert.assertNotNull(em.find(Help.class, 1L));
        helpService.deleteById(1L);
        Assert.assertNull(em.find(Help.class, 1L));
    }
}
