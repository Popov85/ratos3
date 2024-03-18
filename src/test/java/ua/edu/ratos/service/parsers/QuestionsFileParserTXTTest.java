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
public class QuestionsFileParserTXTTest {

    private static final String TYPICAL_CASE = "classpath:files/txt/typical_case.txt";

    private static final String TEST_CASE = "classpath:files/txt/test_case.txt";

    private static final String WIN1251_CASE = "classpath:files/txt/win1251_case.txt";

    private static final String ISSUES_CASE = "classpath:files/txt/with_issues_case.txt";

    private static final String EMPTY_CASE = "classpath:files/txt/empty_case.txt";

    private static final String HEADER_CASE = "classpath:files/txt/header_case.txt";

    private static final String REAL_CASE = "classpath:files/txt/medbio.txt";



    @Test(timeout = 1000L)
    public void parseFileOfTXTFormatShouldYieldCorrectResultOf10QuestionsTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
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
    public void parseFileOfTXTFormatShouldYieldCorrectResultOf3QuestionsTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
        File file = ResourceUtils.getFile(TEST_CASE);
        QuestionsParsingResult result = parser.parseFile(file, "UTF-8");
        assertThat("Result of parsing object is not as expected", result, allOf(
                hasProperty("charset", equalTo("UTF-8")),
                hasProperty("header", not(isEmptyString())),
                hasProperty("questions", hasSize(equalTo(3))),
                hasProperty("issues", hasSize(equalTo(0))),
                hasProperty("invalid", equalTo(0))
        ));
    }

    @Test(timeout = 1000L)
    public void parseFileOfTXTFormatInWin1251ShouldYieldCorrectResultOf10QuestionsTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
        File file = ResourceUtils.getFile(WIN1251_CASE);
        QuestionsParsingResult result = parser.parseFile(file, "windows-1251");
        assertThat("Result of parsing object is not as expected", result, allOf(
                //hasProperty("charset", equalTo("windows-1251")),
                hasProperty("header", isEmptyString()),
                hasProperty("questions", hasSize(equalTo(10))),
                hasProperty("issues", hasSize(equalTo(0))),
                hasProperty("invalid", equalTo(0))
        ));
    }

    @Test(timeout = 1000L)
    public void parseFileOfTXTFormatWithIssuesTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
        File file = ResourceUtils.getFile(ISSUES_CASE);
        QuestionsParsingResult result = parser.parseFile(file, "UTF-8");
        assertThat("Result of parsing object is not as expected", result, allOf(
                hasProperty("charset", equalTo("UTF-8")),
                hasProperty("header", not(isEmptyString())),
                hasProperty("questions", hasSize(equalTo(3))),
                hasProperty("issues", hasSize(equalTo(5))),
                hasProperty("invalid", equalTo(3))
        ));
    }

    @Ignore(value = "For visually testing the output")
    @Test(timeout = 1000L)
    public void parseFileOfTXTFormatTypicalCaseTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
        File file = ResourceUtils.getFile(REAL_CASE);
        QuestionsParsingResult result = parser.parseFile(file, "windows-1251");
        log.debug("Questions = {}", result.getQuestions().size());
        log.debug("Issues = {}", result.getIssues().size());
        log.debug("Invalid = {}", result.getInvalid());
        result.getInvalids().forEach(i->log.debug("Invalid = {}, answers = {}", i.getQuestion(), i.getAnswers()));
        result.getIssues().forEach(i->log.debug("Issue = {}", i));
    }

    @Test(timeout = 1000L, expected = RuntimeException.class)
    public void parseFileOfTXTFormatEmptyShouldYieldExceptionTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
        File file = ResourceUtils.getFile(EMPTY_CASE);
        parser.parseFile(file, "UTF-8");
    }

    @Test(timeout = 1000L, expected = RuntimeException.class)
    public void parseFileOfTXTFormatOnlyHeaderShouldYieldExceptionTest() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
        File file = ResourceUtils.getFile(HEADER_CASE);
        parser.parseFile(file, "UTF-8");
    }
}

