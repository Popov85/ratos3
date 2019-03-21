package ua.edu.ratos.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.it.service.session.sessions.SessionTestMCQSupport;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.question.QuestionDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Only for native Tomcat sessions.
 * With Spring Session on, the test does not work!
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GenericSessionControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionTestMCQSupport sessionTestMCQSupport;

    @Test
    @WithMockUser(authorities = {"ROLE_STUDENT"})
    @Sql(scripts = {"/scripts/init.sql", "/scripts/session/case_simple_mcq_scheme_non_dynamic_not_batched.sql", "/scripts/session/case_simple_mcq.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void sessionTest() throws Exception {
        // Track launch time request
        long launchTime = System.nanoTime();
        ResultActions perform = this.mvc.perform(get("/student/session/start?schemeId=1")
                .accept(MediaType.APPLICATION_JSON));
        long launchFinishTime = System.nanoTime();

        MvcResult mvcResult = perform.andReturn();
        HttpSession session = mvcResult.getRequest().getSession();

        SessionData sessionData = (SessionData) session.getAttribute("sessionData");
        Map<Long, QuestionDomain> questionsMap = sessionData.getQuestionsMap();

        MockHttpServletResponse response = mvcResult.getResponse();
        String batchOut = response.getContentAsString();
        log.debug("First batch = {}", batchOut);

        BatchOutDto currentBatch = objectMapper.readValue(batchOut, BatchOutDto.class);

        int batchesLeft = currentBatch.getBatchesLeft();

        List<Long> switchNextTimings = new ArrayList<>();

        while (batchesLeft > 0) {
            // Here you show question(s) and perform next() request with BatchIn included
            String content = null;
            if (batchesLeft%2==0) {
                // incorrect
                content = "{\"responses\" : {}}";
            } else {
                // correct
                QuestionSessionOutDto nextQuestion = currentBatch.getBatch().get(0);
                Map<Long, Response> correct = sessionTestMCQSupport.getCorrectResponseMCQMap(questionsMap, nextQuestion.getQuestionId(), true);
                BatchInDto batchInDto = new BatchInDto(correct);
                content = objectMapper.writeValueAsString(batchInDto);
                log.debug("Content = {}", content);
                // {"responses":{"8":{"className":"ua.edu.ratos.service.domain.response.ResponseMCQ","questionId":8,"answersIds":[29,30,31]}}}
            }
            // Track switch time between requests
            long startTime = System.nanoTime();
            MockHttpServletResponse r = this.mvc.perform(post("/student/session/next")
                    .session((MockHttpSession)session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();
            long finishTime = System.nanoTime();

            switchNextTimings.add((finishTime-startTime)/1000000);

            currentBatch = objectMapper.readValue(r.getContentAsString(), BatchOutDto.class);
            log.debug("Next batch = {}", currentBatch);
            batchesLeft = currentBatch.getBatchesLeft();
        }
        // Here you show the last question(s) and launch finish request
        log.debug("About to launch finish request...");

        MockHttpServletResponse result = this.mvc.perform(MockMvcRequestBuilders.post("/student/session/finish-batch")
                .session((MockHttpSession)session)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"responses\" : {}}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        ResultOutDto resultOutDto = objectMapper.readValue(result.getContentAsString(), ResultOutDto.class);
        log.debug("Result = {}", resultOutDto);

        // Expected result:
        assertTrue(resultOutDto.isPassed());
        assertEquals(50.0, resultOutDto.getPercent().doubleValue(), 0.01);
        assertEquals(3.0, resultOutDto.getGrade().doubleValue(), 0.01);
        assertEquals(0, resultOutDto.getPoints().intValue());
        assertNull(resultOutDto.getThemeResults());
        assertNull(resultOutDto.getQuestionResults());

        log.debug("Launch time = {} ms", (launchFinishTime-launchTime)/1000000);
        switchNextTimings.forEach(t ->log.debug("Next switch time was = {} ms",t));
    }
}
