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
import ua.edu.ratos.dao.entity.SchemeTheme;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.SchemeThemeService;
import ua.edu.ratos.service.dto.in.SchemeThemeInDto;
import javax.persistence.EntityManager;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SchemeThemeServiceTestIT {

    public static final String JSON_NEW = "classpath:json/scheme_theme_in_dto_new.json";

    public static final String FIND = "select s from SchemeTheme s join fetch s.settings where s.schemeThemeId=:schemeThemeId";

    @Autowired
    private SchemeThemeService schemeThemeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_theme_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        SchemeThemeInDto dto = objectMapper.readValue(json, SchemeThemeInDto.class);
        final Long returnedId = schemeThemeService.save(1L, dto);
        Assert.assertEquals(1L, returnedId.longValue());
        final SchemeTheme foundSchemeTheme =
                (SchemeTheme) em.createQuery(FIND)
                        .setParameter("schemeThemeId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundSchemeTheme);
        Assert.assertEquals(1L, foundSchemeTheme.getScheme().getSchemeId().longValue());
        Assert.assertEquals(1L, foundSchemeTheme.getTheme().getThemeId().longValue());
        Assert.assertEquals(3, foundSchemeTheme.getSettings().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_theme_settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeSettingsTest() throws Exception {
        // Given 4 settings objects associated with SchemeTheme, let's remove one (4th)
        schemeThemeService.removeSettings(1L, 4L);
        final SchemeTheme foundSchemeTheme =
                (SchemeTheme) em.createQuery(FIND).setParameter("schemeThemeId", 1L).getSingleResult();
        Assert.assertNotNull(foundSchemeTheme);
        Assert.assertEquals(1L, foundSchemeTheme.getScheme().getSchemeId().longValue());
        Assert.assertEquals(1L, foundSchemeTheme.getTheme().getThemeId().longValue());
        Assert.assertEquals(3, foundSchemeTheme.getSettings().size());
    }

    @Test(expected = RuntimeException.class)
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_theme_settings_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void removeSettingsLastTest() throws Exception {
        // Given 4 settings objects associated with SchemeTheme, let's remove all one by one
        schemeThemeService.removeSettings(1L, 1L);
        schemeThemeService.removeSettings(1L, 2L);
        schemeThemeService.removeSettings(1L, 3L);
        // expect exception here
        schemeThemeService.removeSettings(1L, 4L);
    }


}
