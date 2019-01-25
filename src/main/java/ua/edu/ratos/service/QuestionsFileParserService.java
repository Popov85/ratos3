package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.edu.ratos.dao.entity.Language;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.service.dto.in.FileInDto;
import ua.edu.ratos.service.transformer.domain_to_dto.QuestionsParsingResultDtoTransformer;
import ua.edu.ratos.service.dto.out.QuestionsParsingResultOutDto;
import ua.edu.ratos.service.parsers.*;
import ua.edu.ratos.service.utils.CharsetDetector;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionsFileParserService {
    /**
     * Currently, only MCQ (Type ID = 1) are supported to be saved via file
     */
    private static final long DEFAULT_QUESTION_TYPE_ID = 1L;

    @Autowired
    private EntityManager em;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionsParsingResultDtoTransformer transformer;

    @Autowired
    private CharsetDetector charsetDetector;

    /**
     * Parses multipart file and saves all the questions to DB
     * @param multipartFile file with questions
     * @param dto metadata of the file
     * @return result on parsing and saving
     */
    public synchronized QuestionsParsingResultOutDto parseAndSave(@NonNull MultipartFile multipartFile, @NonNull FileInDto dto) throws IOException {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        QuestionsFileParser parser = getParser(extension);
        final String encoding = charsetDetector.detectEncoding(multipartFile.getInputStream());
        final QuestionsParsingResult parsingResult = parser.parseStream(multipartFile.getInputStream(), encoding);
        if (dto.isConfirmed()) {
            save(parsingResult.getQuestions(), dto);
            log.debug("Saved questions into the DB after confirmation, {}", parsingResult.getQuestions().size());
            return transformer.toDto(parsingResult, true);
        }
        if (parsingResult.issuesOf(QuestionsParsingIssue.Severity.MAJOR)==0) {
            save(parsingResult.getQuestions(), dto);
            log.debug("Saved questions into the DB with no major issues, {}", parsingResult.getQuestions().size());
            return transformer.toDto(parsingResult, true);
        } else {
            return transformer.toDto(parsingResult, false);
        }
    }

    private void save(@NonNull List<QuestionMCQ> parsedQuestions, @NonNull FileInDto dto) {
        // First, Enrich questions with ThemeDomain, Language and Type, second for each non-null helpAvailable, enrich it with Staff
        QuestionType type = em.getReference(QuestionType.class, DEFAULT_QUESTION_TYPE_ID);
        Theme theme = em.getReference(Theme.class, dto.getThemeId());
        Language language = em.getReference(Language.class, dto.getLangId());
        Staff staff = em.getReference(Staff.class, dto.getStaffId());
        final List<Question> questions = new ArrayList<>();
        parsedQuestions.forEach(q->{
            q.setTheme(theme);
            q.setType(type);
            q.setLang(language);
            if (q.getHelp().isPresent()) q.getHelp().get().setStaff(staff);
            questions.add(q);
        });
        questionService.saveAll(questions);
    }

    private QuestionsFileParser getParser(@NonNull String extension) {
        if ("txt".equals(extension)) return new QuestionsFileParserTXT();
        if ("rtp".equals(extension) || "xtt".equals(extension)) return new QuestionsFileParserRTP();
        throw new UnsupportedOperationException("Unsupported file extension: only .txt/.rtp/.xtt files are supported");
    }
}
