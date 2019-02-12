package ua.edu.ratos.it.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.repository.PhraseRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PhraseRepositoryTestIT {

    @Autowired
    private PhraseRepository phraseRepository;


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/phrase_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOneForUpdateTest() {
        Phrase phrase = phraseRepository.findOneForEdit(1L);
        Assert.assertNotNull(phrase);
        Assert.assertNotNull(phrase.getStaff());
        Assert.assertTrue(phrase.getResource().isPresent());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/phrase_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdTest() {
        final Page<Phrase> foundPage = phraseRepository.findAllByStaffId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(4, foundPage.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/phrase_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByStaffIdAndFirstLettersTest() {
        final Page<Phrase> foundPage = phraseRepository.findAllByStaffIdAndPhraseLettersContains(2L, "trans", PageRequest.of(0, 50));
        Assert.assertEquals(2, foundPage.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/phrase_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdTest() {
        final Page<Phrase> foundPage = phraseRepository.findAllByDepartmentId(1L, PageRequest.of(0, 50));
        Assert.assertEquals(7, foundPage.getContent().size());
    }

    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/phrase_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByDepartmentIdAndFirstLettersTest() {
        final Page<Phrase> foundPage = phraseRepository.findAllByDepartmentIdAndPhraseLettersContains(1L, "trans", PageRequest.of(0, 50));
        Assert.assertEquals(2, foundPage.getContent().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql", "/scripts/phrase_test_data_many.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllTest() {
        final Page<Phrase> foundPage = phraseRepository.findAll(PageRequest.of(0, 50));
        Assert.assertEquals(7, foundPage.getContent().size());
    }
}
