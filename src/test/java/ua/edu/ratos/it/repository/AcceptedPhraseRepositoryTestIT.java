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
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
import ua.edu.ratos.domain.repository.AcceptedPhraseRepository;
import ua.edu.ratos.it.ActiveProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AcceptedPhraseRepositoryTestIT {

    @Autowired
    AcceptedPhraseRepository phraseRepository;


    @Test
    @Sql(scripts = {"/scripts/init.sql","/scripts/accepted_phrase_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllLastUsedByStaffIdTest() {
        Assert.assertEquals(7, phraseRepository.findAll().size());
        final Page<AcceptedPhrase> foundPage = phraseRepository.findAllLastUsedByStaffId(1L, PageRequest.of(0, 20));
        Assert.assertEquals(4, foundPage.getContent().size());
    }


    @Test
    @Sql(scripts = {"/scripts/init.sql","/scripts/accepted_phrase_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllLastUsedByStaffIdAndFirstLettersTest() {
        Assert.assertEquals(7, phraseRepository.findAll().size());
        final Page<AcceptedPhrase> foundPage = phraseRepository.findAllLastUsedByStaffIdAndFirstLetters(2L, "trans", PageRequest.of(0, 20));
        Assert.assertEquals(2, foundPage.getContent().size());
    }
}
