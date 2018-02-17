package ua.zp.zsmu.ratos.learning_session.service.parser;

import ua.zp.zsmu.ratos.learning_session.model.Answer;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andrey on 1/14/2018.
 *
 * Key points:
 *
 * 1) Empty ('') question and answer titles are not allowed (such the element will be deleted automatically);
 * 2) Empty answers list is not allowed (Such the question will be deleted)
 * 3)
 *
 */
public class QuestionsFileParser implements Parser {

    private final String filename;

    private List<Question> questions;

    private List<Issue> issues = new ArrayList<>();

    private Iterator<Question> questionIterator;

    private Iterator<Answer> answerIterator;

    private Question question;

    public QuestionsFileParser(final String filename) {
        this.filename = filename;
    }

    public ParsingResult<Question, Issue> readFile(LineReader lineReader) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line = br.readLine();
            while (line != null) {
                lineReader.readLine(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        questions = lineReader.getParsingResult().getQuestions();
        issues = lineReader.getParsingResult().getIssues();
        check();
        return new ParsingResult<>(questions, issues);
    }

    // Check every question, particularly the last one
    private void check() {
        for (questionIterator = questions.iterator(); questionIterator.hasNext(); ) {
            question = questionIterator.next();
            if ("".equals(question.getQuestion())) {
                addIssue("Empty question title: question deleted",
                        Issue.Severity.HIGH, Issue.Part.HEADER, true);
            } else {
                int sum = 0;
                List<Answer> answers = question.getAnswers();
                List<Answer> correct = new ArrayList<>();
                for (answerIterator = answers.iterator(); answerIterator.hasNext(); ) {
                    Answer answer = answerIterator.next();
                    if ("".equals(answer.getAnswer())) {
                        addIssue("Empty answer: answer deleted",
                                Issue.Severity.LOW, Issue.Part.ANSWER, false);
                    } else {
                        int correctness = answer.getCorrect();
                        if (correctness > 0) {
                            sum += correctness;
                            correct.add(answer);
                        }
                    }
                }
                if (question.getAnswers().isEmpty()) {
                    addIssue("Empty answers list: question deleted",
                            Issue.Severity.HIGH, Issue.Part.HEADER, true);
                } else {
                    if (sum <= 0) {
                        addIssue("Incorrect sum, no right answers: question deleted",
                                Issue.Severity.HIGH, Issue.Part.ANSWER, true);
                    } else if (sum!= 100) {// Single answer value>100 is prohibited during parsing
                        adjust(correct);// Try to re-assign values to answers
                    }
                }
            }
        }
        return;
    }

    // If there is a mistake in answer values assignment, this method tries to resolve this automatically
    private void adjust(List<Answer> correct) {
        List<Integer> values;
        switch (correct.size()) {
            case 2:
                values = Arrays.asList(50, 50);
                break;
            case 3:
                values = Arrays.asList(33, 33, 34);
                break;
            case 4:
                values = Arrays.asList(25, 25, 25, 25);
                break;
            case 5:
                values = Arrays.asList(20, 20, 20, 20, 20);
                break;
            default:
                addIssue("Too many answers, cannot resolve correctness: question deleted",
                        Issue.Severity.HIGH, Issue.Part.QUESTION, true);
                return;
        }
        for (int i = 0; i < correct.size(); i++) {
            correct.get(i).setRequired(false);
            correct.get(i).setCorrect(values.get(i));
        }
        addIssue("Question values were changed: question modified",
                Issue.Severity.MEDIUM, Issue.Part.QUESTION, true);
    }

    private void addIssue(String description, Issue.Severity severity, Issue.Part part, boolean isQuestion) {
        Issue issue = new Issue(description, severity, part)
                .setTitle((question.getQuestion().length()>30) ? question.getQuestion().substring(0,30)+"...":question.getQuestion());
        issues.add(issue);
        if (isQuestion) {
            if (severity==Issue.Severity.HIGH) questionIterator.remove();
        } else {
            answerIterator.remove();
        }
    }

}
