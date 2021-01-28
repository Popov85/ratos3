package ua.edu.ratos.service.transformer.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.service.dto.out.QuestionsParsingIssueOutDto;
import ua.edu.ratos.service.dto.out.QuestionsParsingResultOutDto;
import ua.edu.ratos.service.dto.out.question.QuestionMCQOutDto;
import ua.edu.ratos.service.parsers.QuestionsParsingIssue;
import ua.edu.ratos.service.parsers.QuestionsParsingResult;
import ua.edu.ratos.service.transformer.QuestionMapper;
import ua.edu.ratos.service.transformer.QuestionsParsingIssueMapper;
import ua.edu.ratos.service.transformer.QuestionsParsingResultTransformer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class QuestionsParsingResultTransformerImpl implements QuestionsParsingResultTransformer {

    private final QuestionsParsingIssueMapper questionsParsingIssueMapper;

    private final QuestionMapper questionMapper;

    @Override
    public QuestionsParsingResultOutDto toDto(@NonNull final QuestionsParsingResult parsingResult, boolean saved) {
        QuestionsParsingResultOutDto dto = new QuestionsParsingResultOutDto()
                .setCharset(parsingResult.getCharset())
                .setQuestions(parsingResult.questions())
                .setInvalid(parsingResult.getInvalid())
                .setIssues(parsingResult.issues())
                .setMajorIssues((int)parsingResult.issuesOf(QuestionsParsingIssue.Severity.MAJOR))
                .setMediumIssues((int)parsingResult.issuesOf(QuestionsParsingIssue.Severity.MEDIUM))
                .setMinorIssues((int)parsingResult.issuesOf(QuestionsParsingIssue.Severity.MINOR))
                .setAllIssues(toListOfIssuesDto(parsingResult.getIssues()))
                .setSaved(saved);
        if (saved) dto.setContent(toListOfQuestionsDto(parsingResult.getQuestions()));
        return dto;
    }

    private Set<QuestionsParsingIssueOutDto> toListOfIssuesDto(@NonNull final List<QuestionsParsingIssue> issues) {
        return issues.stream().map(questionsParsingIssueMapper::toDto).collect(Collectors.toSet());
    }

    private Set<QuestionMCQOutDto> toListOfQuestionsDto(@NonNull final List<QuestionMCQ> questions) {
        return questions.stream().map(questionMapper::toDto).collect(Collectors.toSet());
    }
}
