package ua.edu.ratos.service.parsers;

import lombok.extern.slf4j.Slf4j;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.dao.entity.answer.AnswerMCQ;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;

import java.util.List;
import java.util.Optional;

import static ua.edu.ratos.service.parsers.QuestionsParsingIssue.Part.*;
import static ua.edu.ratos.service.parsers.QuestionsParsingIssue.Severity.*;

@Slf4j
public final class QuestionsFileParserRTP extends AbstractQuestionsFileParser implements QuestionsFileParser {

    private static final String PREFIX = ".rtp/.xtt parsing error: ";

    private QuestionMCQ currentQuestion;

    private boolean questionStartExpected = true;
    private boolean answerStartExpected;
    private boolean hintStartExpected;

    private boolean questionContinuationPossible;
    private boolean answerContinuationPossible;
    private boolean hintContinuationPossible;

    private String helpLine = "";

    @Override
    void parseLine(String line) {
        if (startStatus == false) throw new IllegalStateException("Parsing process yet not started!");
        if (!line.trim().isEmpty()) {
            String trimmedLine = line.trim();
            Character firstChar = trimmedLine.charAt(0);
            if (firstChar == '#') {
                readQuestion(trimmedLine);
            } else if (firstChar == '%') {
                readAnswer(trimmedLine);
            } else if (firstChar == '@') {
                readHint(trimmedLine);
            } else {// Goes String line (question title most probably), or the continuation of answerIds title or hint title
                readString(trimmedLine);
            }
        }
    }

    private void readQuestion(String line) {
        if (!questionStartExpected) {
            String description = PREFIX + "unexpected question start!";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MAJOR, QUESTION, currentRow, currentLine));
        }

        // Check if Help of previous question is not empty
        removeHelpIfEmpty();

        currentQuestion = QuestionMCQ.createEmpty();
        currentQuestion.setLevel(getLevel(line));

        questions.add(currentQuestion);

        questionStartExpected = false;
        answerStartExpected = false;
        hintStartExpected = false;

        questionContinuationPossible = true;
        answerContinuationPossible = false;
        hintContinuationPossible = false;

        this.helpLine = "";
    }

    // if help happens to be an empty string - just remove it
    private void removeHelpIfEmpty() {
        if (currentQuestion != null) {
            Optional<Help> help = currentQuestion.getHelp();
            if (help.isPresent()) {
                Help h = help.get();
                if (h.getHelp() == null || h.getHelp().isEmpty()) currentQuestion.clearHelps();
            }
        }
    }

    private byte getLevel(String line) {
        final String s = line.replaceAll("\\s+", "");
        int number;
        try {
            number = Integer.parseInt(s.substring(1));
            if (number < 1 || number > 3) throw new RuntimeException();
        } catch (NumberFormatException e) {
            String description = PREFIX + "failed to parse difficulty level: level 1 is accepted";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MINOR, QUESTION, currentRow, currentLine));
            return 1;
        }
        return (byte) number;
    }

    private void readAnswer(String line) {
        if (!answerStartExpected) {
            String description = PREFIX + "unexpected answerIds start!";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MAJOR, ANSWER, currentRow, currentLine));
        }
        try {
            AnswerMCQ answer = createAnswer(line);
            currentQuestion.addAnswer(answer);
        } catch (Exception e) {
            String description = PREFIX + "parsing error, answerIds skipped, details: " + e.getMessage();
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MAJOR, ANSWER, currentRow, currentLine));
        }

        questionStartExpected = true;
        answerStartExpected = true;
        hintStartExpected = true;

        questionContinuationPossible = false;
        answerContinuationPossible = true;
        hintContinuationPossible = false;
    }

    private void readHint(String line) {
        if (!hintStartExpected) {
            String description = PREFIX + "unexpected hint start!";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MAJOR, HINT, currentRow, currentLine));
        }

        this.helpLine = extractHintStart(line);

        Help help = new Help();
        help.setName("automatic #" + System.nanoTime());
        help.setHelp(this.helpLine);

        currentQuestion.addHelp(help);

        questionStartExpected = true;
        answerStartExpected = false;
        hintStartExpected = false;

        questionContinuationPossible = false;
        answerContinuationPossible = false;
        hintContinuationPossible = true;
    }

    // "@ Some hint starts here" - like string
    private String extractHintStart(String line) {
        String result = line.substring(1, line.length());
        return result.trim();
    }

    private void readString(String line) {
        if (questionContinuationPossible) {
            currentQuestion.setQuestion(currentQuestion.getQuestion() +" "+line);

            questionStartExpected = false;
            answerStartExpected = true;
            hintStartExpected = false;
        }
        if (answerContinuationPossible) {
            final List<AnswerMCQ> answers = currentQuestion.getAnswers();
            String currentAnswer = (answers.get(answers.size() - 1)).getAnswer();
            (answers.get(answers.size() - 1)).setAnswer(currentAnswer +" "+ line);

            questionStartExpected = true;
            answerStartExpected = true;
            hintStartExpected = true;
        }
        if (hintContinuationPossible) {

            String updatedHelp = this.helpLine +" "+ line;
            Help help = new Help();
            help.setName("automatic #" + System.nanoTime());
            help.setHelp(updatedHelp);
            currentQuestion.addHelp(help);

            questionStartExpected = true;
            answerStartExpected = false;
            hintStartExpected = false;
        }
    }

    // Parses %!100%-like answerIds String and creates Answer object
    private AnswerMCQ createAnswer(String line) {
        // Try to find the second closing %-sign - the end of answerIds prefix
        int stop = line.indexOf('%', 1);
        if (stop == -1) throw new RuntimeException("Incorrect answerIds prefix!");
        String value = line.substring(1, stop).replaceAll("\\s+", "");
        if (value.length() < 1 || value.length() > 4)
            throw new RuntimeException("Inappropriate answerIds prefix length!");

        boolean isRequired = false;
        if (value.charAt(0) == '!') isRequired = true;

        String percentage = isRequired ? value.substring(1) : value.substring(0);
        int percent = Integer.parseInt(percentage);
        //Percent must be within 0-100! If not, take 0
        if (percent < 0 || percent > 100) {
            percent = 0;
            String description = PREFIX + "percentage is out of range, 0 is accepted";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MEDIUM, ANSWER, currentRow, currentLine));
        }
        //Incorrect answers cannot be required! Fix typo
        if (percent == 0 && isRequired) {
            isRequired = false;
            String description = PREFIX + "incorrect answers cannot be required, corrected";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MINOR, ANSWER, currentRow, currentLine));
        }
        String answer = line.substring(stop + 1).trim();
        AnswerMCQ a = new AnswerMCQ();
        a.setAnswer(answer);
        a.setPercent((short) percent);
        a.setRequired(isRequired);
        return a;
    }

}
