package ua.edu.ratos.service.parsers;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;
import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class AbstractFileParserTest {

    private static final String EMPTY_SOURCE_FILE= "classpath:files/sample_empty.txt";
    private static final String EMPTY_SOURCE_FILE_WITH_HEADER= "classpath:files/sample_header.txt";
    private static final String SOURCE_FILE_WITH_NO_HEADER = "classpath:files/sample_no_header.txt";
    private static final String REGULAR_SOURCE_FILE_WITH_HEADER = "classpath:files/sample.txt";


    @Test(expected = RuntimeException.class)
    public void parseFileShouldThrowRuntimeExceptionTest1() throws Exception {
        AbstractFileParserMock mock = new AbstractFileParserMock();
        File file = ResourceUtils.getFile(EMPTY_SOURCE_FILE);
        mock.parseFile(file,"UTF-8");
    }


    @Test(expected = RuntimeException.class)
    public void parseFileShouldThrowRuntimeExceptionTest2() throws Exception {
        AbstractFileParserMock mock = new AbstractFileParserMock();
        File file = ResourceUtils.getFile(EMPTY_SOURCE_FILE_WITH_HEADER);
        mock.parseFile(file,"UTF-8");
    }

    @Test(timeout = 1000L)
    public void parseFileShouldExtractEmptyHeaderTest() throws Exception {
        AbstractFileParserMock mock = new AbstractFileParserMock();
        File file = ResourceUtils.getFile(SOURCE_FILE_WITH_NO_HEADER);
        mock.parseFile(file,"UTF-8");
        assertThat(mock.header, isEmptyString());
    }

    @Test(timeout = 1000L)
    public void parseFileShouldExtractHeaderTest() throws Exception {
        AbstractFileParserMock mock = new AbstractFileParserMock();
        File file = ResourceUtils.getFile(REGULAR_SOURCE_FILE_WITH_HEADER);
        mock.parseFile(file,"UTF-8");
        assertThat(mock.header, not(isEmptyString()));
    }
}
