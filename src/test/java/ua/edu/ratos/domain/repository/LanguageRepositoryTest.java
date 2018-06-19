package ua.edu.ratos.domain.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.domain.entity.Language;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class LanguageRepositoryTest {
    @Autowired
    private LanguageRepository languageRepository;

    @Test
    public void saveTest() {
        Language lang = new Language();
        lang.setName("Deutch");
        lang.setAbbreviation("DE");
        languageRepository.save(lang);
    }

    @Test
    public void findAll() {
        languageRepository.findAll().forEach(System.out::println);
    }
}
