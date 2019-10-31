package ua.edu.ratos.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.edu.ratos.service.PhraseService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PhraseController.class)
public class PhraseControllerTest {

    public static final String JSON_NEW = "{\"phrase\": \"phrase #1\"}";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhraseService phraseService;

    @Test(timeout = 2000)
    @WithMockUser(authorities = {"ROLE_INSTRUCTOR"})
    public void saveTest() throws Exception {
        when(phraseService.save(any())).thenReturn(1L);
        //given(phraseService.save(any())).willReturn(1L);
        this.mvc.perform(post("/instructor/phrases").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JSON_NEW))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/instructor/phrases/1"))
                .andExpect(content().string(""));
        verify(phraseService, atLeastOnce()).save(any());
    }
}
