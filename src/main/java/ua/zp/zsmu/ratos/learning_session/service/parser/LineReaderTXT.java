package ua.zp.zsmu.ratos.learning_session.service.parser;

import ua.zp.zsmu.ratos.learning_session.model.Answer;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * * Example of .rtp-file:
     #
     Question Line 1
     Question Line 2
     Question Line 3
     1
     Correct answer 1
     Continuation of correct answer 2
     Continuation of correct answer 3
     0
     Wrong answer 1
     Continuation of wrong answer 2
     0
     Wrong answer 1
     0
     Wrong answer 1
     Wrong answer 2
     Wrong answer 3
 * <p>
 * Structure:
 * 1) # - signifies a new question; the levels of difficulty are not supported; by default level 1 is accepted;
 * Chars after '#' symbol are ignored not counted as question title;
 * 2) Chars after either 0 or 1 are ignored! Answer text should be placed on the next line.
 * 3) 1 - signifies a new correct answer; 0 - signifies a new incorrect answer
 * <p>
 * Key points:
 * 1) White spaces are ignored
 * 2) Header is ignored
 * 3) If unexpected element is identified - RuntimeException is thrown
 * 4) Multiple correct answers are supported (if 2 correct - then each accepts 50% of correctness, all are not required)
 * 5) Questions and answers can me multiline.
 * 6) @ hints are not supported
 * <p>
 * Created by Andrey on 1/24/2018.
 */
public class LineReaderTXT implements LineReader {

    // Result List of Questions
    private List<Question> questions = new ArrayList<>();
    private List<Issue> issues = new ArrayList<>();
    private Question currentQuestion;
    private List<Answer> currentAnswers;
    // Separates a header from questions body
    private boolean headerExpected = true;
    private int lineCounter = 0;
    private int lineCounterAfterHeader = 0;

    private boolean questionStartExpected;
    private boolean answerStartExpected;

    private boolean questionContinuationPossible;
    private boolean answerContinuationPossible;

    private int answerCounter = 0;

    // How strict the syntactical analysis is:
    // if true - RuntimeException is thrown,
    // if false - parsing process proceeds with adding an issue
    private boolean isStrict;

    public LineReaderTXT(boolean isStrict) {
        this.isStrict = isStrict;
    }

    @Override
    public ParsingResult<Question, Issue> getParsingResult() {
        return new ParsingResult<>(questions, issues);
    }

    @Override
    public void readLine(String line) {
        lineCounter++;
        // Cut out the header
        if (headerExpected && (line.trim().isEmpty() || line.trim().charAt(0) != '#')) {// header goes, just ignore
            return;
        } else {// Header finishes
            headerExpected = false;
            lineCounterAfterHeader++;
        }
        if (!headerExpected) {// First question after header
            if (lineCounterAfterHeader == 1) questionStartExpected = true;
            if (!line.trim().isEmpty()) {
                String trimmedLine = line.trim();
                Character firstChar = trimmedLine.charAt(0);
                if (firstChar == '#') {
                    readQuestion();
                } else if (firstChar == '0' || firstChar == '1') {
                    int value = Character.getNumericValue(firstChar);
                    readAnswer(value);
                } else {// Goes String line (question title most probably), or the continuation of answer title
                    readString(trimmedLine);
                }
            }
            return;
        }
    }

    private void readQuestion() {
        if (!questionStartExpected) {
            String message = ".txt parsing error: Unexpected question start!";
            if (isStrict) {
                throw new RuntimeException(message+" Line: " + lineCounter);
            } else {
                addIssue(message, Issue.Severity.HIGH, Issue.Part.QUESTION);
            }
        }
        // Create a new question and add it to the resulting list
        currentQuestion = new Question();
        questions.add(currentQuestion);
        currentAnswers = new ArrayList<>();
        currentQuestion.setAnswers(currentAnswers);

        questionStartExpected = false;
        answerStartExpected = false;

        questionContinuationPossible = true;
        answerContinuationPossible = false;

        answerCounter = 0;
    }

    private void readAnswer(int value) {
        if (!answerStartExpected) {
            String message = ".txt parsing error: Unexpected answer distractor!";
            if (isStrict) {
                throw new RuntimeException(message+" Line: " + lineCounter);
            } else {
                addIssue(message, Issue.Severity.HIGH, Issue.Part.ANSWER);
            }
        }

        Answer answer = new Answer("", value * 100, false);
        currentAnswers.add(answer);

        questionStartExpected = true;
        answerStartExpected = false;

        questionContinuationPossible = false;
        answerContinuationPossible = true;

        answerCounter++;
    }

    private void readString(String line) {
        if (questionContinuationPossible) {
            currentQuestion.setQuestion(currentQuestion.getQuestion() + line);

            questionStartExpected = false;
            answerStartExpected = true;
        }
        if (answerContinuationPossible) {
            String currentAnswer = currentQuestion.getAnswers().get(answerCounter - 1).getAnswer();
            currentQuestion.getAnswers().get(answerCounter - 1).setAnswer(currentAnswer + line);

            questionStartExpected = true;
            answerStartExpected = true;

        }
    }

    private void addIssue(String description, Issue.Severity severity, Issue.Part part) {
        Issue issue = new Issue(description, severity, part)
                .setLine(lineCounter)
                .setTitle((currentQuestion.getQuestion().length() > 30) ?
                        currentQuestion.getQuestion().substring(0, 30) + "..." :
                        currentQuestion.getQuestion());
        issues.add(issue);
    }
}
