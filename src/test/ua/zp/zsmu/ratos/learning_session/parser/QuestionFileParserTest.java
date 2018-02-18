package ua.zp.zsmu.ratos.learning_session.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.zp.zsmu.ratos.learning_session.dao.PersistenceContext;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.service.parser.*;

/**
 * Created by Andrey on 2/17/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class QuestionFileParserTest {

    @Test(timeout=1000)
    public void readFileJSONTest() {
        ParsingResult<Question, Issue> result = null;
        LineReaderJSON lineReaderJSON = new LineReaderJSON("src/testResources/questions.json");
        try {
            result = lineReaderJSON.getParsingResult();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        Assert.assertTrue(result.getQuestions().size()>0);

        System.out.println("QUESTIONS#:"+result.getQuestions().size());
        result.getQuestions().forEach(question->System.out.println(question));

        System.out.println("ISSUES#:"+result.getIssues().size());
        System.out.println("SEVERE#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.HIGH).count());
        System.out.println("MEDIUM#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.MEDIUM).count());
        System.out.println("LOW#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.LOW).count());
        result.getIssues().forEach(issue->System.out.println(issue));
    }

    @Test(timeout=1000)
    public void readFileRTPTest() {
        QuestionsFileParser parser = new QuestionsFileParser("src/testResources/file.rtp");
        ParsingResult<Question, Issue> result = null;
        try {
            LineReader lineReader = new LineReaderRTP(false);
            result = parser.readFile(lineReader);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        Assert.assertTrue(result.getQuestions().size()>0);

        System.out.println("QUESTIONS#:"+result.getQuestions().size());
        result.getQuestions().forEach(question->System.out.println(question));

        System.out.println("ISSUES#:"+result.getIssues().size());
        System.out.println("SEVERE#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.HIGH).count());
        System.out.println("MEDIUM#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.MEDIUM).count());
        System.out.println("LOW#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.LOW).count());
        result.getIssues().forEach(issue->System.out.println(issue));
    }

    @Test(timeout=1000)
    public void readFileTXTTest() {
        QuestionsFileParser parser = new QuestionsFileParser("src/testResources/txt.txt");
        ParsingResult<Question, Issue> result = null;
        try {
            LineReader lineReader = new LineReaderTXT(false);
            result = parser.readFile(lineReader);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        Assert.assertTrue(result.getQuestions().size()>0);

        System.out.println("QUESTIONS#:"+result.getQuestions().size());
        result.getQuestions().forEach(question->System.out.println(question));

        System.out.println("ISSUES#:"+result.getIssues().size());
        System.out.println("SEVERE#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.HIGH).count());
        System.out.println("MEDIUM#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.MEDIUM).count());
        System.out.println("LOW#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.LOW).count());
        result.getIssues().forEach(issue->System.out.println(issue));
    }

}
