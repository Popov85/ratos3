package ua.edu.ratos.service.transformer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.ThemeView;
import ua.edu.ratos.dao.entity.ThemeViewId;
import ua.edu.ratos.service.dto.out.view.ThemeViewOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.ThemeViewDtoTransformer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
public class ThemeViewExtendedDtoTransformerTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public ThemeViewDtoTransformer transformer() {
            return new ThemeViewDtoTransformer();
        }
    }

    @Autowired
    private ThemeViewDtoTransformer transformer;

    private Set<ThemeView> themeViews = new HashSet<>();

    @Before
    public void init() {
        ThemeViewId id1 = buildId(1L, 1L);
        ThemeViewId id2 = buildId(1L, 2L);
        ThemeViewId id3 = buildId(1L, 3L);
        ThemeViewId id4 = buildId(1L, 4L);
        ThemeViewId id5 = buildId(1L, 5L);

        ThemeView t1 = buildThemeView(id1, "Theme #1", "MCQ", (short)10, (short)10,(short)10, (short)30);
        ThemeView t2 = buildThemeView(id2, "Theme #1", "FBSQ", (short)20, (short)15,(short)10, (short)45);
        ThemeView t3 = buildThemeView(id3, "Theme #1", "FBMQ", (short)20, (short)15,(short)10, (short)45);
        ThemeView t4 = buildThemeView(id4, "Theme #1", "MQ", (short)10, (short)10,(short)0, (short)20);
        ThemeView t5 = buildThemeView(id5, "Theme #1", "SQ", (short)10, (short)0,(short)0, (short)10);


        ThemeViewId id6 = buildId(2L, 1L);
        ThemeViewId id7 = buildId(2L, 2L);
        ThemeViewId id8 = buildId(2L, 3L);

        ThemeView t6 = buildThemeView(id6, "Theme #2", "MCQ", (short)10, (short)10,(short)10, (short)30);
        ThemeView t7 = buildThemeView(id7, "Theme #2", "FBSQ", (short)10, (short)15,(short)10, (short)35);
        ThemeView t8 = buildThemeView(id8, "Theme #2", "FBMQ", (short)10, (short)15,(short)10, (short)35);


        ThemeViewId id9 = buildId(3L, 1L);
        ThemeViewId id10 = buildId(3L, 5L);

        ThemeView t9 = buildThemeView(id9, "Theme #3", "MCQ", (short)10, (short)10,(short)10, (short)30);
        ThemeView t10 = buildThemeView(id10, "Theme #3", "SQ", (short)10, (short)10,(short)0, (short)20);

        themeViews.addAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
    }


    @Test
    public void toDtoTest() {

        final Set<ThemeViewOutDto> result = transformer.toDto(themeViews);
        result.forEach(r->{
            if (r.getThemeId()==1) {
                Assert.assertEquals(5, r.getTypeDetails().size());
                Assert.assertEquals(150, r.getTotalQuestions());
            } else if (r.getThemeId()==2) {
                Assert.assertEquals(3, r.getTypeDetails().size());
                Assert.assertEquals(100, r.getTotalQuestions());
            } else if (r.getThemeId()==3) {
                Assert.assertEquals(2, r.getTypeDetails().size());
                Assert.assertEquals(50, r.getTotalQuestions());
            }
        });
    }


    private ThemeViewId buildId(Long themeId, Long typeId) {
        return  new ThemeViewId()
                .setTypeId(typeId)
                .setThemeId(themeId);
    }

    private ThemeView buildThemeView(ThemeViewId id, String theme, String type, short l1, short l2, short l3, int questions) {
        return new ThemeView()
                .setThemeViewId(id)
                .setTheme(theme)
                .setType(type)
                .setL1(l1)
                .setL2(l2)
                .setL3(l3)
                .setQuestions(questions);
    }
}
