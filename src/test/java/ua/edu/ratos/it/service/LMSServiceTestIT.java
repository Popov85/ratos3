package ua.edu.ratos.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.LMSService;
import ua.edu.ratos.service.dto.in.LMSInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class LMSServiceTestIT {

    public static final String JSON_NEW = "classpath:json/lms_in_dto_new.json";

    public static final String FIND = "select l from LMS l join fetch l.credentials left join fetch l.origins where l.lmsId=:lmsId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LMSService lmsService;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        LMSInDto dto = objectMapper.readValue(json, LMSInDto.class);
        Long lmsId = lmsService.save(dto);
        Assert.assertEquals(4L, lmsId.longValue());
        LMS lms = (LMS) em.createQuery(FIND).setParameter("lmsId", 4L).getSingleResult();
        Assert.assertNotNull(lms);
        Assert.assertEquals("ratos_consumer_key", lms.getCredentials().getKey());
        Assert.assertEquals("ratos_client_secret", lms.getCredentials().getSecret());
        Assert.assertEquals(2, lms.getOrigins().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateNameTest(){
        lmsService.updateName(1L, "Open edx updated");
        LMS lms = (LMS) em.createQuery(FIND).setParameter("lmsId", 1L).getSingleResult();
        Assert.assertNotNull(lms);
        Assert.assertEquals("Open edx updated", lms.getName());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateCredentialsTest() {
        lmsService.updateCredentials(1L, "updated key", "updated secret");
        LMS lms = (LMS) em.createQuery(FIND).setParameter("lmsId", 1L).getSingleResult();
        Assert.assertNotNull(lms);
        Assert.assertEquals("updated key", lms.getCredentials().getKey());
        Assert.assertEquals("updated secret", lms.getCredentials().getSecret());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addOriginTest() {
        lmsService.addOrigin(1L, "http://localhost:111");
        LMS lms = (LMS) em.createQuery(FIND).setParameter("lmsId", 1L).getSingleResult();
        Assert.assertNotNull(lms);
        Assert.assertEquals(3, lms.getOrigins().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeOriginTest() {
        lmsService.removeOrigin(1L);
        LMS lms = (LMS) em.createQuery(FIND).setParameter("lmsId", 1L).getSingleResult();
        Assert.assertNotNull(lms);
        Assert.assertEquals(1, lms.getOrigins().size());
    }
}
