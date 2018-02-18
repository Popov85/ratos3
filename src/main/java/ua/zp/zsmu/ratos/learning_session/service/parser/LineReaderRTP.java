package ua.zp.zsmu.ratos.learning_session.service.parser;

import ua.zp.zsmu.ratos.learning_session.model.Answer;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * * Parser that can parse .rtp-files
 * Example of .rtp-file:
     #1
     Question Line 1
     Question Line 2
     Question Line 3
     %!100% Correct answer 1
     Continuation of correct answer 2
     Continuation of correct answer 3
     %0% Wrong answer 1
     Continuation of wrong answer 2
     %0% Wrong answer 1
     %0% Wrong answer 1
     Wrong answer 2
     Wrong answer 3
 *
 * @ Hint 0
 * Continuation of some hint 1
 * Continuation of some hint 2
 * <p>
 * Structure:
 * 1) # - signifies a new question; after in goes the level of difficulty; if no number present - level 1 is accepted;
 * Chars after '#' symbol are not counted as question title;
 * 2) %!100% - signifies a new answer; ! - signifies the question is required; Number - signifies its correctness
 * 3) @ - signifies a hint string
 * <p>
 * Key points:
 * 1) White spaces are ignored
 * 2) Header is ignored
 * 3) If unexpected element is identified - RuntimeException is thrown
 * 4) Sum of values for one question cannot be greater than 100
 * 5) Questions, answers and hints can have multiple strings
 * 6) Empty ('') questions and answers are not allowed; empty hints are allowed
 *
 * Created by Andrey on 1/24/2018.
 */
public class LineReaderRTP implements LineReader {

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
    private boolean hintStartExpected;

    private boolean questionContinuationPossible;
    private boolean answerContinuationPossible;
    private boolean hintContinuationPossible;

    private int answerCounter = 0;

    // How strict the syntactical analysis is:
    // if true - RuntimeException is thrown,
    // if false - parsing process proceeds with adding an issue
    private boolean isStrict;

    public LineReaderRTP(boolean isStrict) {
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
                    readQuestion(trimmedLine);
                } else if (firstChar == '%') {
                    readAnswer(trimmedLine);
                } else if (firstChar == '@') {
                    readHint(trimmedLine);
                } else {// Goes String line (question title most probably), or the continuation of answer title or hint title
                    readString(trimmedLine);
                }
            }
            return;
        }
    }


    private void readQuestion(String line) {
        if (!questionStartExpected) {
            String message = ".rtp parsing error: Unexpected question start!";
            if (isStrict) {
                throw new RuntimeException(message+" Line: " + lineCounter);
            } else {
                addIssue(message, Issue.Severity.HIGH, Issue.Part.QUESTION);
            }
        }
        // Create a new question and add it to the resulting list
        currentQuestion = new Question();
        if (line.length() == 1) {
            Issue issue = new Issue("Difficulty level is not present: level 1 is accepted",
                    Issue.Severity.LOW, Issue.Part.HEADER)
                    .setLine(lineCounter);
            issues.add(issue);
        } else {// Check if value is a correct number from 1 to 3
            char numberExpected = line.replaceAll("\\s+", "").charAt(1);
            int number = Character.getNumericValue(numberExpected);
            if (number == -1) {
                Issue issue = new Issue("Failed to parse the question difficulty: level 1 is accepted",
                        Issue.Severity.MEDIUM, Issue.Part.HEADER)
                        .setLine(lineCounter);
                issues.add(issue);
            } else if (number < 1 || number > 3) {
                Issue issue = new Issue("Unsupported question difficulty: level 1 is accepted",
                        Issue.Severity.LOW, Issue.Part.HEADER)
                        .setLine(lineCounter);
                issues.add(issue);
            } else {
                currentQuestion.setDifficulty(number);
            }
        }
        questions.add(currentQuestion);
        currentAnswers = new ArrayList<>();
        currentQuestion.setAnswers(currentAnswers);

        questionStartExpected = false;
        answerStartExpected = false;
        hintStartExpected = false;

        questionContinuationPossible = true;
        answerContinuationPossible = false;
        hintContinuationPossible = false;

        answerCounter = 0;
    }


    private void readAnswer(String line) {
        if (!answerStartExpected) {
            String message = ".rtp parsing error: Unexpected answer distractor!";
            if (isStrict) {
                throw new RuntimeException(message+" Line: " + lineCounter);
            } else {
                addIssue(message, Issue.Severity.HIGH, Issue.Part.ANSWER);
            }
        }
        // Answer will be created anyways, even if error occurs;
        Answer answer = createAnswer(line);

        currentAnswers.add(answer);
        currentQuestion.setAnswers(currentAnswers);

        questionStartExpected = true;
        answerStartExpected = true;
        hintStartExpected = true;

        questionContinuationPossible = false;
        answerContinuationPossible = true;
        hintContinuationPossible = false;
        answerCounter++;
    }

    private void readHint(String line) {
        if (!hintStartExpected) {
            String message = ".rtp parsing error: Unexpected hint!";
            if (isStrict) {
                throw new RuntimeException(message+" Line: " + lineCounter);
            } else {
                addIssue(message, Issue.Severity.HIGH, Issue.Part.HINT);
            }
        }

        currentQuestion.setHelp(line);

        questionStartExpected = true;
        answerStartExpected = false;
        hintStartExpected = false;

        questionContinuationPossible = false;
        answerContinuationPossible = false;
        hintContinuationPossible = true;
    }


    private void readString(String line) {
        if (questionContinuationPossible) {
            currentQuestion.setQuestion(currentQuestion.getQuestion() + line);

            questionStartExpected = false;
            answerStartExpected = true;
            hintStartExpected = false;
        }
        if (answerContinuationPossible) {
            String currentAnswer = currentQuestion.getAnswers().get(answerCounter - 1).getAnswer();
            currentQuestion.getAnswers().get(answerCounter - 1).setAnswer(currentAnswer + line);

            questionStartExpected = true;
            answerStartExpected = true;
            hintStartExpected = true;
        }
        if (hintContinuationPossible) {
            currentQuestion.setHelp(currentQuestion.getHelp() + line);

            questionStartExpected = true;
            answerStartExpected = false;
            hintStartExpected = false;
        }
    }


    // Parses %!100% - like answer String and creates Answer object
    private Answer createAnswer(String line) {
        int stop = 0;
        boolean isRequired = false;
        int percent = 0;
        try {
            stop = line.indexOf('%', 1);// Find the end of answer prefix (second %-sign)
            if (stop == -1)
                throw new RuntimeException("Incorrect answer prefix!");
            String value = line.substring(1, stop).replaceAll("\\s+", "");
            if (value.length() < 1 || value.length() > 4)
                throw new RuntimeException("Inappropriate answer prefix length!");
            isRequired = false;
            if (value.charAt(0) == '!') isRequired = true;
            String percentage;
            if (isRequired) {
                percentage = value.substring(1);
            } else {
                percentage = value.substring(0);
            }
            percent = Integer.parseInt(percentage);
            if (percent < 0 || percent > 100)
                throw new RuntimeException("Percentage is out of range!");
            if (percent == 0 && isRequired)
                throw new RuntimeException("Incorrect answers cannot be required!");
        } catch (Exception e) {
            Issue issue = new Issue(e.getMessage(), Issue.Severity.MEDIUM, Issue.Part.ANSWER)
                .setLine(lineCounter);
            issues.add(issue);
            return new Answer(); // It will be deleted later, just not to stop parsing process
        }
        String answer = line.substring(stop + 1).trim();
        return new Answer(answer, percent, isRequired);
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

