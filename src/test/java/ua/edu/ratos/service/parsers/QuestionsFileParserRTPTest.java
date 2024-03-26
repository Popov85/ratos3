package ua.edu.ratos.service.parsers;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;


@Slf4j
@RunWith(JUnit4.class)
public class QuestionsFileParserRTPTest {

    private static final String TYPICAL_CASE = "classpath:files/rtp/typical_case.rtp";

    private static final String TEST_CASE = "classpath:files/rtp/test_case.rtp";

    private static final String REAL_CASE = "classpath:files/xtt/physiology.xtt";

    @Test(timeout = 1000L)
    public void parseFileOfRTPFormatTypicalCaseShouldYieldCorrectResultOf510QuestionsTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserRTP();
        File file = ResourceUtils.getFile(TYPICAL_CASE);
        QuestionsParsingResult result = parser.parseFile(file, "UTF-8");
        assertThat("Result of parsing object is not as expected", result, allOf(
                hasProperty("charset", equalTo("UTF-8")),
                hasProperty("header", isEmptyString()),
                hasProperty("questions", hasSize(equalTo(10))),
                hasProperty("issues", hasSize(equalTo(0))),
                hasProperty("invalid", equalTo(0))
        ));
    }

    @Test(timeout = 1000L)
    public void parseFileOfRTPFormatTestCaseShouldYieldCorrectResultOf5QuestionsTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserRTP();
        File file = ResourceUtils.getFile(TEST_CASE);
        QuestionsParsingResult result = parser.parseFile(file, "UTF-8");
        //result.getQuestions().forEach(q->log.debug("Question = {}, help = {}, answers = {}", q, q.getHelp(), q.getAnswers()));
        //result.getIssues().forEach(i->log.debug("Issue = {}", i));
        assertThat("Result of parsing object is not as expected", result, allOf(
                hasProperty("charset", equalTo("UTF-8")),
                hasProperty("header", not(isEmptyString())),
                hasProperty("questions", hasSize(equalTo(5))),
                hasProperty("issues", hasSize(equalTo(2))),
                hasProperty("invalid", equalTo(1))
        ));
    }

    @Ignore(value = "For visually testing the output")
    @Test(timeout = 1000L)
    public void parseFileOfXTTFormatTypicalCaseTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserRTP();
        File file = ResourceUtils.getFile(REAL_CASE);
        QuestionsParsingResult result = parser.parseFile(file, "windows-1251");
        log.debug("Questions = {}", result.getQuestions().size());
        log.debug("Issues = {}", result.getIssues().size());
        log.debug("Invalid = {}", result.getInvalid());
        result.getInvalids().forEach(i->log.debug("Invalid = {}, answers = {}", i.getQuestion(), i.getAnswers()));
        result.getIssues().forEach(i->log.debug("Issue = {}", i));
    }
}

