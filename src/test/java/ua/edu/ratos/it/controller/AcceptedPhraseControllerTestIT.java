package ua.edu.ratos.it.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.edu.ratos.it.ActiveProfile;
import ua.edu.ratos.service.AcceptedPhraseService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AcceptedPhraseControllerTestIT {

    public static final String JSON_NEW = "{\"phrase\": \"phrase #1\", \"staffId\": 1}";
    public static final String JSON_UPD = "classpath:json/accepted_phrase_in_dto_upd.json";

    @Autowired
    private MockMvc mvc;

    /*@MockBean
    private AcceptedPhraseService acceptedPhraseService;*/

    @Ignore("Security issues")
    @Test
    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/test_data_clear_"+ ActiveProfile.NOW+".sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.post("/instructor/accepted-phrases/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_NEW)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/instructor/accepted-phrases/1"))
                .andExpect(content().string(""));
    }
}
