package ua.edu.ratos.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Theme;
import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @Test
    public void saveTest() {
        Theme theme = new Theme();
        theme.setName("Java Collections");
        themeRepository.save(theme);
    }

    @Test
    public void findAllTest() {
        themeRepository.findAll().forEach(t->log.info(t.toString()));
    }

    @Test
    public void updateTest() {
        themeRepository.updateById("Java Basic Operators", 1L);
        final String actualName = themeRepository.findById(1L).get().getName();
        assertEquals("Java Basic Operators", actualName);
    }

    @Test(expected = RuntimeException.class)
    public void deleteExceptionTest() throws Exception {
        try {
            themeRepository.deleteById(1L);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void deleteSuccessTest() throws Exception {
        themeRepository.deleteById(2L);
    }

}
