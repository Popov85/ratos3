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
import ua.edu.ratos.dao.entity.SchemeThemeSettings;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.grading.SchemeThemeSettingsService;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;
import javax.persistence.EntityManager;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SchemeThemeSettingsServiceTestIT {

    public static final String JSON_NEW = "classpath:json/scheme_theme_settings_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/scheme_theme_settings_in_dto_upd.json";

    @Autowired
    private SchemeThemeSettingsService schemeThemeSettingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/scheme_theme_settings_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        SchemeThemeSettingsInDto dto = objectMapper.readValue(json, SchemeThemeSettingsInDto.class);
        schemeThemeSettingsService.save(dto);
        final SchemeThemeSettings foundSettings = em.find(SchemeThemeSettings.class, 1L);
        Assert.assertNotNull(foundSettings);
        Assert.assertEquals(1L, foundSettings.getSchemeTheme().getSchemeThemeId().longValue());
        Assert.assertEquals(1L, foundSettings.getType().getTypeId().longValue());
        Assert.assertEquals(20, foundSettings.getLevel1());
        Assert.assertEquals(10, foundSettings.getLevel2());
        Assert.assertEquals(5, foundSettings.getLevel3());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/scheme_theme_settings_test_data.sql", "/scripts/scheme_theme_settings_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        SchemeThemeSettingsInDto dto = objectMapper.readValue(json, SchemeThemeSettingsInDto.class);
        schemeThemeSettingsService.update(1L, dto);
        final SchemeThemeSettings foundSettings = em.find(SchemeThemeSettings.class, 1L);
        Assert.assertNotNull(foundSettings);
        Assert.assertEquals(1L, foundSettings.getSchemeTheme().getSchemeThemeId().longValue());
        Assert.assertEquals(1L, foundSettings.getType().getTypeId().longValue());
        Assert.assertEquals(10, foundSettings.getLevel1());
        Assert.assertEquals(0, foundSettings.getLevel2());
        Assert.assertEquals(0, foundSettings.getLevel3());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/scheme_theme_settings_test_data.sql", "/scripts/scheme_theme_settings_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTest() {
        Assert.assertNotNull(em.find(SchemeThemeSettings.class, 1L));
        schemeThemeSettingsService.deleteById(1L);
        Assert.assertNull(em.find(SchemeThemeSettings.class, 1L));
    }

}
