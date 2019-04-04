package ua.edu.ratos.service.generator;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
public class StringGeneratorTest {

    @Autowired
    private StringGenerator stringGenerator;

    @TestConfiguration
    static class StringGeneratorTestContextConfiguration {

        @Bean
        public StringGenerator stringGenerator() {
            return new StringGenerator();
        }

        @Bean
        public Rnd rnd() {
            return new Rnd();
        }
    }


    @Test
    public void createTextQuestionTest() {
        String text = stringGenerator.createText(50, 150, 5, 15, true);
        assertNotNull(text);
        assertTrue(text.length()>=250);
        log.debug("Text = {}", text);
        log.debug("Text length = {}", text.length());
    }

    @Test
    public void createTextAnswerTest() {
        String text = stringGenerator.createText(1, 50, 1, 15, false);
        assertNotNull(text);
        log.debug("Text = {}", text);
        log.debug("Text length = {}", text.length());
    }
}
