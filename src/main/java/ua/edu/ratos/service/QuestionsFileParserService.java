package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.edu.ratos.dao.entity.QuestionType;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.Theme;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.dao.entity.question.QuestionMCQ;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.FileInDto;
import ua.edu.ratos.service.dto.out.QuestionsParsingResultOutDto;
import ua.edu.ratos.service.parsers.*;
import ua.edu.ratos.service.transformer.domain_to_dto.QuestionsParsingResultDtoTransformer;
import ua.edu.ratos.service.utils.CharsetDetector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class QuestionsFileParserService {

    //Currently, only MCQ (Type ID = 1) are supported to be saved via file
    private static final long DEFAULT_QUESTION_TYPE_ID = 1L;

    @PersistenceContext
    private final EntityManager em;

    private final QuestionService questionService;

    private final QuestionsParsingResultDtoTransformer transformer;

    private final CharsetDetector charsetDetector;

    private final SecurityUtils securityUtils;

    /**
     * Parses multipart file and saves all the questions to DB
     * @param multipartFile file with questions
     * @param dto metadata of the file
     * @return result on parsing and saving
     */
    public QuestionsParsingResultOutDto parseAndSave(@NonNull final MultipartFile multipartFile, @NonNull final FileInDto dto) throws IOException {
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

    private void save(@NonNull final List<QuestionMCQ> parsedQuestions, final @NonNull FileInDto dto) {
        // First, Enrich question with Theme, Language and Type, secondly for each non-null Help, enrich it with Staff
        QuestionType type = em.getReference(QuestionType.class, DEFAULT_QUESTION_TYPE_ID);
        Theme theme = em.getReference(Theme.class, dto.getThemeId());
        Staff staff = em.getReference(Staff.class, securityUtils.getAuthStaffId());
        final List<Question> questions = new ArrayList<>();
        parsedQuestions.forEach(q->{
            q.setTheme(theme);
            q.setType(type);
            if (q.getHelp().isPresent()) q.getHelp().get().setStaff(staff);
            questions.add(q);
        });
        questionService.saveAll(questions);
    }

    private QuestionsFileParser getParser(@NonNull final String extension) {
        if ("txt".equals(extension)) return new QuestionsFileParserTXT();
        if ("rtp".equals(extension) || "xtt".equals(extension)) return new QuestionsFileParserRTP();
        throw new UnsupportedOperationException("Unsupported file extension: only .txt/.rtp/.xtt files are supported");
    }
}
