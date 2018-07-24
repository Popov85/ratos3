package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.domain.entity.question.QuestionType;
import ua.edu.ratos.domain.repository.LanguageRepository;
import ua.edu.ratos.domain.repository.QuestionTypeRepository;
import ua.edu.ratos.service.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionsFileParserService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private QuestionTypeRepository typeRepository;

    @Autowired
    private QuestionService questionService;

    public QuestionsParsingResult parseAndSave(@NonNull String fileType, @NonNull File filename, @NonNull String charset, @NonNull Long langId, boolean isConfirmed) {
        QuestionsFileParser parser = getParser(fileType);
        final QuestionsParsingResult parsingResult = parser.parseFile(filename, charset);
        if (isConfirmed) {
            save(fileType, langId, parsingResult);
            return parsingResult;
        }
        if (parsingResult.issuesOf(QuestionsParsingIssue.Severity.MAJOR)==0) {
            save(fileType, langId, parsingResult);
            return parsingResult;
        } else {
            return parsingResult;
        }
    }

    private void save(@NonNull String fileType, @NonNull Long langId, @NonNull QuestionsParsingResult parsingResult) {
        final List<QuestionMultipleChoice> parsedQuestions = parsingResult.getQuestions();
        // First, Enrich questions with Language and Type
        QuestionType type = typeRepository.getOne(1L);
        Language language = languageRepository.getOne(langId);
        final ArrayList<Question> questions = new ArrayList<>();
        parsedQuestions.forEach(q->{
            q.setType(type);
            q.setLang(language);
            questions.add(q);
        });
        questionService.saveAll(questions);
        log.debug("Parsed and saved :: {} questions of type :: {} ", questions.size(), fileType);
    }

    private QuestionsFileParser getParser(@NonNull String fileType) {
        if ("rtp".equals(fileType)) return new QuestionsFileParserRTP();
        if ("txt".equals(fileType)) return new QuestionsFileParserTXT();
        throw new UnsupportedOperationException("Unsupported question type");
    }
}
