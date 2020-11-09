package ua.edu.ratos.service._it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.ActiveProfile;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.dao.entity.grading.SchemeFourPoint;
import ua.edu.ratos.dao.entity.grading.SchemeFreePoint;
import ua.edu.ratos.dao.entity.grading.SchemeTwoPoint;
import ua.edu.ratos.dao.repository.SchemeRepository;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.dto.in.SchemeInDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchemeServiceTestIT {

    private static final String JSON_NEW = "classpath:json/scheme_in_dto_new.json";
    private static final String FIND_WITH_COLLECTIONS = "select s from Scheme s left join fetch s.groups left join fetch s.themes t left join fetch t.settings where s.schemeId=:schemeId";
    private static final String FIND_GRADING_FOUR = "select s from SchemeFourPoint s join fetch s.fourPointGrading where s.schemeId=:schemeId";
    private static final String FIND_GRADING_FREE = "select s from SchemeFreePoint s join fetch s.freePointGrading where s.schemeId=:schemeId";
    private static final String FIND_GRADING_TWO = "select s from SchemeTwoPoint s join fetch s.twoPointGrading where s.schemeId=:schemeId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        SchemeInDto dto = objectMapper.readValue(json, SchemeInDto.class);
        // Actual test begins
        schemeService.save(dto);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Saved Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("groups", hasSize(3)),
                hasProperty("themes", hasSize(3))
        ));
        // Verify insertion into scheme_four_point table
        final SchemeFourPoint foundFour = (SchemeFourPoint) em.createQuery(FIND_GRADING_FOUR).setParameter("schemeId", 1L).getSingleResult();
        assertNotNull("SchemeFourPoint is not found", foundFour);
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateNameTest() {
        // Update only name
        schemeService.updateName(1L, "Updated name");
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Updated name"))
        ));
    }

    @Test(timeout = 10000)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateIsActiveTest() {
        // Deactivate
        schemeService.updateIsActive(1L, false);
        final Scheme scheme = (Scheme) em.createQuery(FIND_WITH_COLLECTIONS).setParameter("schemeId", 1L).getSingleResult();
        assertThat("Updated Scheme object is not as expected", scheme, allOf(
                hasProperty("schemeId", equalTo(1L)),
                hasProperty("name", equalTo("Scheme #1")),
                hasProperty("active", equalTo(false))
        ));
    }

}
