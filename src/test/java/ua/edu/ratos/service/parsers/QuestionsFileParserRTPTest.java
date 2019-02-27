package ua.edu.ratos.service.parsers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;
import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

@RunWith(JUnit4.class)
public class QuestionsFileParserRTPTest {

    private static final String REGULAR_SOURCE = "classpath:files/sample.rtp";

    @Test(timeout = 1000L)
    public void parseFileShouldYieldCorrectResult() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserRTP();
        File file = ResourceUtils.getFile(REGULAR_SOURCE);
        QuestionsParsingResult result = parser.parseFile(file, "UTF-8");

        assertThat(result.getHeader(), not(isEmptyString()));
        Assert.assertTrue(result.getQuestions().size()>0);
    }
}

