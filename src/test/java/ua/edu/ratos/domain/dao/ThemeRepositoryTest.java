package ua.edu.ratos.domain.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.model.Theme;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;


    //@Commit
    @Test
    public void saveTest() {
        Theme theme = new Theme();
        theme.setName("Java Operators");

        Theme theme2 = new Theme();
        theme2.setName("Java Strings");

        Theme theme3 = new Theme();
        theme3.setName("Java Generics");

        themeRepository.saveAll(Arrays.asList(theme, theme2, theme3));
    }


    @Test
    public void findAllTest() {
        System.out.println("All themes");
        themeRepository.findAll().forEach(System.out::println);
    }

}
