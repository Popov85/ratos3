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
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.HelpService;
import ua.edu.ratos.service.dto.in.HelpInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class HelpServiceTestIT {

    public static final String JSON_NEW = "classpath:json/help_in_dto_new.json";

    public static final String HELP_NAME = "help name";
    public static final String HELP_TEXT = "Please, refer to section #1 T#1";
    public static final String HELP_NAME_UPD = "help name upd";
    public static final String HELP_TEXT_UPD = "Please, refer to section #2 T#1 upd";

    public static final String FIND = "select h from Help h join fetch h.staff left join fetch h.resources r where h.helpId=:helpId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HelpService helpService;


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        HelpInDto dto = objectMapper.readValue(json, HelpInDto.class);
        helpService.save(dto);
        final Help foundHelp = (Help) em.createQuery(FIND).setParameter("helpId",1L).getSingleResult();
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NAME, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT, foundHelp.getHelp());
        Assert.assertTrue(foundHelp.getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateNameTest() {
        helpService.updateName(1L, HELP_NAME_UPD);
        final Help foundHelp = (Help) em.createQuery(FIND).setParameter("helpId",1L).getSingleResult();
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NAME_UPD, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT, foundHelp.getHelp());
        Assert.assertNotNull(foundHelp.getStaff());
        Assert.assertTrue(foundHelp.getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateHelpTest() {
        helpService.updateHelp(1L, HELP_TEXT_UPD);
        final Help foundHelp = (Help) em.createQuery(FIND).setParameter("helpId",1L).getSingleResult();
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NAME, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT_UPD, foundHelp.getHelp());
        Assert.assertNotNull(foundHelp.getStaff());
        Assert.assertTrue(foundHelp.getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateResourceTest() {
        helpService.updateResource(1L, 2L);
        final Help foundHelp = (Help) em.createQuery(FIND).setParameter("helpId",1L).getSingleResult();
        Assert.assertNotNull(foundHelp);
        Assert.assertEquals(HELP_NAME, foundHelp.getName());
        Assert.assertEquals(HELP_TEXT, foundHelp.getHelp());
        Assert.assertNotNull(foundHelp.getStaff());
        Assert.assertTrue(foundHelp.getResource().isPresent());
        Assert.assertEquals(2L, foundHelp.getResource().get().getResourceId().longValue());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/help_test_data.sql", "/scripts/help_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest() {
        Assert.assertNotNull(em.find(Help.class, 1L));
        helpService.deleteById(1L);
        Assert.assertNull(em.find(Help.class, 1L));
    }
}
