package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.repository.lms.LMSRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class LMSRepositoryTestIT {

    @Autowired
    private LMSRepository lmsRepository;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByConsumerKeyTest() {
        LMS lms = lmsRepository.findByConsumerKey("ratos_consumer_key_5");
        Assert.assertNotNull(lms);
        Assert.assertEquals("Moodle-3 local", lms.getName());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForEditTest() {
        LMS lms = lmsRepository.findOneForEditById(6L);
        Assert.assertNotNull(lms);
        Assert.assertEquals("Organisation #2", lms.getOrganisation().getName());
        Assert.assertEquals("ratos_consumer_key_5", lms.getCredentials().getKey());
        Assert.assertEquals("ratos_client_secret_5", lms.getCredentials().getSecret());
        Assert.assertEquals("1p0", lms.getLtiVersion().getVersion());
        Assert.assertEquals(2, lms.getOrigins().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrgIdTest() {
        Slice<LMS> all = lmsRepository.findAllByOrgId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(3, all.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByOrgIdAndNameLettersContainsTest() {
        Slice<LMS> all = lmsRepository.findAllByOrgIdAndNameLettersContains(2L, "local", PageRequest.of(0, 50));
        Assert.assertEquals(2, all.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/lms_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllAdminTest() {
        Slice<LMS> all = lmsRepository.findAllAdmin(PageRequest.of(0, 50));
        Assert.assertEquals(6, all.getContent().size());
    }


}
