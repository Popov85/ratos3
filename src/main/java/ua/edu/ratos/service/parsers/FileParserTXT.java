package ua.edu.ratos.service.parsers;

import ua.edu.ratos.domain.Answer;
import ua.edu.ratos.domain.Question;
import ua.edu.ratos.domain.TypeOfQuestion;
import ua.edu.ratos.domain.answer.AnswerTypeA;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.edu.ratos.service.parsers.Issue.Part.*;
import static ua.edu.ratos.service.parsers.Issue.Severity.HIGH;

public class FileParserTXT extends AbstractFileParser implements FileParser {

    private static final String PREFIX = ".txt parsing error: ";

    private Question currentQuestion;

    private boolean questionStartExpected = true;
    private boolean answerStartExpected;

    private boolean questionContinuationPossible;
    private boolean answerContinuationPossible;

    @Override
    public void parseLine(String line) {
        if (startStatus == false) throw new IllegalStateException("Parsing process yet not started!");
        if (!line.trim().isEmpty()) {
            String trimmedLine = line.trim();
            Character firstChar = trimmedLine.charAt(0);
            if (firstChar == '#') {
                readQuestion();
            } else if (firstChar == '0' || firstChar == '1') {
                boolean isCorrect = (firstChar == '1') ? true : false;
                readAnswer(isCorrect);
            } else {// Goes String line (question title most probably), or the continuation of answer title
                readString(trimmedLine);
            }
        }
    }

    private void readQuestion() {
        if (!questionStartExpected) {
            String description = PREFIX + "unexpected question start!";
            issues.add(new Issue(description, HIGH, QUESTION, currentRow, currentLine));
        }

        currentQuestion = new Question();
        currentQuestion.setQuestion("");
        currentQuestion.setLevel((byte)1);
        currentQuestion.setType(new TypeOfQuestion(1));
        currentQuestion.setResource(Optional.empty());
        currentQuestion.setHelp(Optional.empty());
        currentQuestion.setAnswers(new ArrayList<>());

        questions.add(currentQuestion);

        questionStartExpected = false;
        answerStartExpected = false;

        questionContinuationPossible = true;
        answerContinuationPossible = false;
    }

    private void readAnswer(boolean isCorrect) {
        if (!answerStartExpected) {
            String description = PREFIX + "unexpected answer start!";
            issues.add(new Issue(description, HIGH, ANSWER, currentRow, currentLine));
        }

        AnswerTypeA answer = new AnswerTypeA();
        answer.setAnswer("");
        answer.setCorrect(isCorrect);

        currentQuestion.getAnswers().add(answer);

        questionStartExpected = true;
        answerStartExpected = false;

        questionContinuationPossible = false;
        answerContinuationPossible = true;
    }

    private void readString(String line) {
        if (questionContinuationPossible) {
            String question = currentQuestion.getQuestion();
            currentQuestion.setQuestion((question.isEmpty()) ? line : question + "\n"+ line);

            questionStartExpected = false;
            answerStartExpected = true;
        }
        if (answerContinuationPossible) {
            final List<Answer> answers = currentQuestion.getAnswers();
            String currentAnswer = ((AnswerTypeA) answers.get(answers.size() - 1)).getAnswer();
            ((AnswerTypeA) answers.get(answers.size() - 1)).setAnswer((currentAnswer.isEmpty()) ? line : currentAnswer +"\n"+ line);

            questionStartExpected = true;
            answerStartExpected = true;
        }
    }
}
