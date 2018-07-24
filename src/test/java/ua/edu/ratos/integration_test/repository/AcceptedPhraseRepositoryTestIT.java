package ua.edu.ratos.integration_test.repository;

import lombok.extern.slf4j.Slf4j;
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

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AcceptedPhraseRepositoryTestIT {

    @Autowired
    AcceptedPhraseRepository phraseRepository;

    @Test
    @Sql(scripts = "/scripts/accepted_phrase_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/accepted_phrase_test_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findTop5LastUsedPhrasesByStaffIdTest() {
        final Page<AcceptedPhrase> foundPage = phraseRepository.findAllLastUsedAcceptedPhrasesByStaffId(1L, PageRequest.of(0, 5));
        final List<AcceptedPhrase> foundPhrases = foundPage.getContent();
        Assert.assertEquals(5, foundPhrases.size());
        foundPhrases.forEach(p->log.debug(p.toString()));
    }

}
