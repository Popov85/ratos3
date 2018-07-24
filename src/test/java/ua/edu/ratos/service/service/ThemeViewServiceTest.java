package ua.edu.ratos.service.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.edu.ratos.domain.entity.ThemeView;
import ua.edu.ratos.domain.repository.ThemeViewRepository;
import ua.edu.ratos.service.ThemeViewService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

// http://www.springboottutorial.com/spring-boot-unit-testing-and-mocking-with-mockito-and-junit
@RunWith(MockitoJUnitRunner.class)
public class ThemeViewServiceTest {

    @Mock
    private ThemeViewRepository themeViewRepositoryMock;

    @InjectMocks
    private ThemeViewService themeViewService = new ThemeViewService();;

    @Test
    public void findAllByThemeId() {
        final List<ThemeView> themeViews = new ArrayList<>();
        ThemeView t1 = new ThemeView();
        t1.setType("MCQ");
        t1.setTotal(10);

        ThemeView t2 = new ThemeView();
        t2.setType("FBSQ");
        t2.setTotal(15);

        ThemeView t3 = new ThemeView();
        t3.setType("FBMQ");
        t3.setTotal(10);

        ThemeView t4 = new ThemeView();
        t4.setType("MQ");
        t4.setTotal(10);

        ThemeView t5 = new ThemeView();
        t5.setType("SQ");
        t5.setTotal(15);

        when(themeViewRepositoryMock.findAllByThemeId(1L)).thenReturn(Arrays.asList(t1, t2, t3, t4, t5));
        assertEquals(60, themeViewService.findByThemeId(1L).getTotalQuantity());
    }
}
