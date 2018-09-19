package ua.edu.ratos.it.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.QuestionsFileParserService;
import ua.edu.ratos.service.dto.entity.FileInDto;
import ua.edu.ratos.service.dto.view.QuestionsParsingResultOutDto;

import javax.persistence.EntityManager;
import java.io.File;
import java.nio.file.Files;

/**
 * @link https://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class QuestionsFileParserServiceTestIT {

    private static final String JSON_TXT = "classpath:json/file_txt_in_dto_new.json";
    private static final String JSON_RTP = "classpath:json/file_rtp_in_dto_new.json";
    private static final String QUESTIONS_TXT = "classpath:files/questions.txt";
    private static final String QUESTIONS_RTP = "classpath:files/questions.rtp";
    private static final String QUESTIONS_TXT_1251 = "classpath:files/questions_windows_1251.txt";

    private static final String FIND = "select q from Question q";

    @Autowired
    private QuestionsFileParserService questionsFileParserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/questions_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void parseAndSaveTXTTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_TXT);
        FileInDto dto = objectMapper.readValue(json, FileInDto.class);
        File file = ResourceUtils.getFile(QUESTIONS_TXT);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        MockMultipartFile mock = new MockMultipartFile("mock", "questions.txt","text/plain", bytes);
        final QuestionsParsingResultOutDto result = questionsFileParserService.parseAndSave(mock, dto);
        Assert.assertEquals(10, result.getQuestions());
        Assert.assertEquals(0, result.getIssues());
        Assert.assertEquals("UTF-8", result.getCharset());
        Assert.assertTrue(result.isSaved());
        Assert.assertEquals(10, em.createQuery(FIND).getResultList().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/questions_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void parseAndSaveRTPTest() throws Exception {
        File json = ResourceUtils.getFile(JSON_RTP);
        FileInDto dto = objectMapper.readValue(json, FileInDto.class);
        File file = ResourceUtils.getFile(QUESTIONS_RTP);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        MockMultipartFile mock = new MockMultipartFile("mock", "questions.rtp","text/plain", bytes);
        final QuestionsParsingResultOutDto result = questionsFileParserService.parseAndSave(mock, dto);
        Assert.assertEquals("UTF-8", result.getCharset());
        Assert.assertEquals(10, result.getQuestions());
        Assert.assertEquals(0, result.getIssues());
        Assert.assertTrue(result.isSaved());
        Assert.assertEquals(10, em.createQuery(FIND).getResultList().size());
    }

    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/questions_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void parseAndSaveTXT1251Test() throws Exception {
        File json = ResourceUtils.getFile(JSON_TXT);
        FileInDto dto = objectMapper.readValue(json, FileInDto.class);
        File file = ResourceUtils.getFile(QUESTIONS_TXT_1251);
        final byte[] bytes = Files.readAllBytes(file.toPath());
        MockMultipartFile mock = new MockMultipartFile("mock", "questions_windows_1251.txt","text/plain", bytes);
        final QuestionsParsingResultOutDto result = questionsFileParserService.parseAndSave(mock, dto);
        Assert.assertEquals(10, result.getQuestions());
        Assert.assertEquals(0, result.getIssues());
        Assert.assertEquals("WINDOWS-1251", result.getCharset());
        Assert.assertTrue(result.isSaved());
        Assert.assertEquals(10, em.createQuery(FIND).getResultList().size());
    }
}
