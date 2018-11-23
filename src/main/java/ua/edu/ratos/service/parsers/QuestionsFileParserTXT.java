package ua.edu.ratos.service.parsers;

import ua.edu.ratos.dao.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.dao.entity.answer.AnswerMultipleChoice;
import java.util.List;
import static ua.edu.ratos.service.parsers.QuestionsParsingIssue.Part.*;
import static ua.edu.ratos.service.parsers.QuestionsParsingIssue.Severity.MAJOR;

public final class QuestionsFileParserTXT extends AbstractQuestionsFileParser implements QuestionsFileParser {

    private static final String PREFIX = ".txt parsing error: ";

    private QuestionMultipleChoice currentQuestion;

    private boolean questionStartExpected = true;
    private boolean answerStartExpected;

    private boolean questionContinuationPossible;
    private boolean answerContinuationPossible;

    @Override
    public void parseLine(String line) {
        if (startStatus == false) throw new IllegalStateException("Parsing control yet not started!");
        if (!line.trim().isEmpty()) {
            String trimmedLine = line.trim();
            Character firstChar = trimmedLine.charAt(0);
            if (firstChar == '#') {
                readQuestion();
            } else if (trimmedLine.length()==1 && (firstChar == '0' || firstChar == '1')) {
                short correct = (firstChar == '1') ? (short) 100 : 0;
                readAnswer(correct);
            } else {// Goes String line (question title most probably), or the continuation of answerIds title
                readString(trimmedLine);
            }
        }
    }

    private void readQuestion() {
        if (!questionStartExpected) {
            String description = PREFIX + "unexpected question start!";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MAJOR, QUESTION, currentRow, currentLine));
        }

        currentQuestion = QuestionMultipleChoice.createEmpty();

        questions.add(currentQuestion);

        questionStartExpected = false;
        answerStartExpected = false;

        questionContinuationPossible = true;
        answerContinuationPossible = false;
    }

    private void readAnswer(short correct) {
        if (!answerStartExpected) {
            String description = PREFIX + "unexpected answerIds start!";
            questionsParsingIssues.add(new QuestionsParsingIssue(description, MAJOR, ANSWER, currentRow, currentLine));
        }

        AnswerMultipleChoice answer = new AnswerMultipleChoice();
        answer.setAnswer("");
        answer.setPercent(correct);

        currentQuestion.addAnswer(answer);

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
            final List<AnswerMultipleChoice> answers = currentQuestion.getAnswers();
            String currentAnswer = (answers.get(answers.size() - 1)).getAnswer();
            (answers.get(answers.size() - 1)).setAnswer((currentAnswer.isEmpty()) ? line : currentAnswer +"\n"+ line);

            questionStartExpected = true;
            answerStartExpected = true;
        }
    }
}

