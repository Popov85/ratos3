package ua.zp.zsmu.ratos.learning_session.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.zp.zsmu.ratos.learning_session.dao.PersistenceContext;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.service.parser.Issue;
import ua.zp.zsmu.ratos.learning_session.service.parser.LineReaderJSON;
import ua.zp.zsmu.ratos.learning_session.service.parser.ParsingResult;
import ua.zp.zsmu.ratos.learning_session.service.parser.QuestionsFileParser;

/**
 * Created by Andrey on 2/17/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class QuestionFileParserTest {

    @Test(timeout=1000)
    public void readFileTest() {
        QuestionsFileParser parser = new QuestionsFileParser("D:\\large.xtt");
        //QuestionsFileParser parser = new QuestionsFileParser("D:\\txt.txt");
        ParsingResult<Question, Issue> result = null;
        LineReaderJSON lineReaderJSON = new LineReaderJSON();
        try {
            result = lineReaderJSON.getParsingResult(); //parser.readFile(new LineReaderRTP(false));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
   /*         try {
                questions = parser.readFile(new LineReaderTXT());
            } catch (Exception e1) {
                throw new RuntimeException("Unable to read the file!",e1);
            }*/
        }
        System.out.println("QUESTIONS#:"+result.getQuestions().size());
        result.getQuestions().forEach(question->System.out.println(question));

        //return (int) players.stream().filter(Player::isActive).count();

        System.out.println("ISSUES#:"+result.getIssues().size());
        System.out.println("SEVERE#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.HIGH).count());
        System.out.println("MEDIUM#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.MEDIUM).count());
        System.out.println("LOW#:"+result.getIssues().stream().filter(severity -> severity.getSeverity()==Issue.Severity.LOW).count());
        result.getIssues().forEach(issue->System.out.println(issue));
    }

}
