package ua.edu.ratos.service.parsers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.service.parsers.QuestionsFileParser;
import ua.edu.ratos.service.parsers.QuestionsFileParserTXT;
import ua.edu.ratos.service.parsers.QuestionsParsingResult;

import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static ua.edu.ratos.service.parsers.QuestionsParsingIssue.Severity.*;

@RunWith(JUnit4.class)
public class QuestionsFileParserTXTTest {

    private static final String REGULAR_SOURCE = "classpath:files/sample.txt";

    @Test(timeout = 1000L)
    public void parseFileShouldYieldCorrectResult() throws Exception {
        QuestionsFileParser parser = new QuestionsFileParserTXT();
        File file = ResourceUtils.getFile(REGULAR_SOURCE);
        QuestionsParsingResult result = parser.parseFile(file, "UTF-8");

        assertThat(result.getHeader(), not(isEmptyString()));
        Assert.assertTrue(result.getQuestions().size()>0);
        Assert.assertTrue(result.getIssues().size()==0);

        /*System.out.println("QUESTIONS#: "+result.questions()
                +" INVALID#: "+result.invalid()
                +" SINGLE#: "+result.questionsOf(true));
        result.getQuestions().forEach(question->System.out.println(question));

        System.out.println("ISSUES#: "+result.issues()+
                " MAJOR#: "+result.issuesOf(MAJOR) +
                " MINOR#: "+result.issuesOf(MINOR));
        result.getIssues().forEach(issue->System.out.println(issue));*/
    }
}

