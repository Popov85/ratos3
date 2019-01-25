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
import ua.edu.ratos.service.SchemeThemeSettingsService;
import ua.edu.ratos.service.dto.in.SchemeThemeSettingsInDto;
import javax.persistence.EntityManager;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SchemeThemeSettingsServiceTestIT {

    public static final String JSON_NEW = "classpath:json/scheme_theme_settings_in_dto_new.json";
    public static final String JSON_UPD = "classpath:json/scheme_theme_settings_in_dto_upd.json";


    public static final String FIND = "select s from SchemeThemeSettings s where s.schemeThemeSettingsId=:schemeThemeSettingsId";

    @Autowired
    private SchemeThemeSettingsService schemeThemeSettingsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_theme_test_data.sql", "/scripts/scheme_theme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_NEW);
        SchemeThemeSettingsInDto dto = objectMapper.readValue(json, SchemeThemeSettingsInDto.class);
        final Long returnedId = schemeThemeSettingsService.save(dto);
        Assert.assertEquals(2L, returnedId.longValue());
        final SchemeThemeSettings foundSchemeThemeSettings =
                (SchemeThemeSettings) em.createQuery(FIND)
                        .setParameter("schemeThemeSettingsId", 2L)
                        .getSingleResult();
        Assert.assertNotNull(foundSchemeThemeSettings);
        Assert.assertEquals(1L, foundSchemeThemeSettings.getSchemeTheme().getSchemeThemeId().longValue());
        Assert.assertEquals(1L, foundSchemeThemeSettings.getType().getTypeId().longValue());
        Assert.assertEquals(20, foundSchemeThemeSettings.getLevel1());
        Assert.assertEquals(10, foundSchemeThemeSettings.getLevel2());
        Assert.assertEquals(5, foundSchemeThemeSettings.getLevel3());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/scheme_theme_test_data.sql", "/scripts/scheme_theme_test_data_one.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_UPD);
        SchemeThemeSettingsInDto dto = objectMapper.readValue(json, SchemeThemeSettingsInDto.class);
        schemeThemeSettingsService.update(1L, dto);
        final SchemeThemeSettings foundSchemeThemeSettings =
                (SchemeThemeSettings) em.createQuery(FIND)
                        .setParameter("schemeThemeSettingsId", 1L)
                        .getSingleResult();
        Assert.assertNotNull(foundSchemeThemeSettings);
        Assert.assertEquals(1L, foundSchemeThemeSettings.getSchemeTheme().getSchemeThemeId().longValue());
        Assert.assertEquals(1L, foundSchemeThemeSettings.getType().getTypeId().longValue());
        Assert.assertEquals(5, foundSchemeThemeSettings.getLevel1());
        Assert.assertEquals(5, foundSchemeThemeSettings.getLevel2());
        Assert.assertEquals(5, foundSchemeThemeSettings.getLevel3());
    }


}
