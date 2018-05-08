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

public class FileParserRTPTest {

    private static final String REGULAR_SOURCE = "classpath:files/sample.rtp";

    @Test(timeout = 1000L)
    public void parseFileShouldYieldCorrectResult() throws Exception {
        FileParser parser = new FileParserRTP();
        File file = ResourceUtils.getFile(REGULAR_SOURCE);
        ParsingResult result = parser.parseFile(file, "UTF-8");

        assertThat(result.getHeader(), not(isEmptyString()));
        Assert.assertTrue(result.getQuestions().size()>0);
        //Assert.assertTrue(result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.HIGH).count()==0);

        System.out.println("QUESTIONS#:"+result.getQuestions().size());
        result.getQuestions().forEach(question->System.out.println(question));

        System.out.println("ISSUES#:"+result.getIssues().size());
        result.getIssues().forEach(issue->System.out.println(issue));
    }
}
