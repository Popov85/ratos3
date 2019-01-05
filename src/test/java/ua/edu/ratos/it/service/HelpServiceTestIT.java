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
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.in.HelpInDto;
import javax.persistence.EntityManager;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HelpServiceTestIT {

    public static final String JSON_NEW = "classpath:json/help_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/help_in_dto_upd.json";

    public static final String HELP_NEW = "help name";
    public static final String HELP_TEXT = "Please, refer to section #1 T#1";

    public static final String HELP_UPD = "help name upd";
    public static final String HELP_TEXT_UPD = "Please, refer to section #2 T#1";
    public static final String RESOURCE_NAME = "Schema#1";
    public static final String RESOURCE_LINK = "https://image.slidesharecdn.com/schema01.jpg";
    public static final String RESOURCE_NAME_UPD = "Schema#2";
    public static final String RESOURCE_LINK_UPD = "https://image.slidesharecdn.com/schema02.jpg";

    public static final String FIND = "select h from Help h left join fetch h.resources r where h.helpId=:helpId";

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HelpService helpService;


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/help_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
        Assert.assertTrue(foundHelp.getResource().isPresent());
        Assert.assertEquals(new Resource(RESOURCE_LINK, RESOURCE_NAME), foundHelp.getResource().get());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        HelpInDto dto = objectMapper.readValue(json, HelpInDto.class);
        helpService.update(1L, dto);
        final Help foundHelp =
            (Help) em.createQuery(FIND)
                .setParameter("helpId",1L)
                .getSingleResult();
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_UPD, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT_UPD, foundHelp.getHelp());
        Assert.assertTrue(foundHelp.getResource().isPresent());
        Assert.assertEquals(new Resource(RESOURCE_LINK_UPD, RESOURCE_NAME_UPD), foundHelp.getResource().get());
    }


    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() {
        Assert.assertNotNull(em.find(Help.class, 1L));
        helpService.deleteById(1L);
        Assert.assertNull(em.find(Help.class, 1L));
    }
}
