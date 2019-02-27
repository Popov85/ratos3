package ua.edu.ratos.it.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.ThemeService;
import ua.edu.ratos.service.dto.out.LevelsOutDto;
import ua.edu.ratos.service.dto.out.ThemeMapOutDto;

import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ThemeServiceTestIT {

    @Autowired
    private ThemeService themeService;

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/theme_map_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getQuestionTypeLevelMapByThemeIdTest()  {
        ThemeMapOutDto themeMapOutDto = themeService.getQuestionTypeLevelMapByThemeId(1L);
        log.debug("result = {}", themeMapOutDto);
        assertEquals(1L, themeMapOutDto.getThemeId());
        assertEquals(21, themeMapOutDto.getTotalByTheme());
        Map<Long, LevelsOutDto> map = themeMapOutDto.getTypeLevelMap();
        assertEquals(5, map.size());

        LevelsOutDto levelsMCQ = map.get(1L);
        assertNotNull(levelsMCQ);
        assertEquals(2, levelsMCQ.getTotalLevel1());
        assertEquals(3, levelsMCQ.getTotalLevel2());
        assertEquals(3, levelsMCQ.getTotalLevel3());
        assertEquals(8, levelsMCQ.getTotal());

        LevelsOutDto levelsFBSQ = map.get(2L);
        assertNotNull(levelsFBSQ);
        assertEquals(1, levelsFBSQ.getTotalLevel1());
        assertEquals(1, levelsFBSQ.getTotalLevel2());
        assertEquals(1, levelsFBSQ.getTotalLevel3());
        assertEquals(3, levelsFBSQ.getTotal());

        LevelsOutDto levelsFBMQ = map.get(3L);
        assertNotNull(levelsFBMQ);
        assertEquals(3, levelsFBMQ.getTotalLevel1());
        assertEquals(0, levelsFBMQ.getTotalLevel2());
        assertEquals(0, levelsFBMQ.getTotalLevel3());
        assertEquals(3, levelsFBMQ.getTotal());

        LevelsOutDto levelsMQ = map.get(4L);
        assertNotNull(levelsMQ);
        assertEquals(3, levelsMQ.getTotalLevel1());
        assertEquals(1, levelsMQ.getTotalLevel2());
        assertEquals(0, levelsMQ.getTotalLevel3());
        assertEquals(4, levelsMQ.getTotal());

        LevelsOutDto levelsSQ = map.get(5L);
        assertNotNull(levelsSQ);
        assertEquals(2, levelsSQ.getTotalLevel1());
        assertEquals(0, levelsSQ.getTotalLevel2());
        assertEquals(1, levelsSQ.getTotalLevel3());
        assertEquals(3, levelsSQ.getTotal());

    }
}
