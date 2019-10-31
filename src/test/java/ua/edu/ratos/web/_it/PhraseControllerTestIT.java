package ua.edu.ratos.web._it;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.service.dto.in.PhraseInDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhraseControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test(timeout = 5000)
    @Ignore("Use WebMvcTest instead!")
    @WithMockUser(authorities = {"ROLE_INSTRUCTOR"})
    public void saveTest() throws Exception {
        PhraseInDto phrase = new PhraseInDto();
        phrase.setPhrase("phrase #1");
        ResponseEntity responseEntity = this.restTemplate.postForObject("/instructor/phrases",
                phrase, ResponseEntity.class);
        System.out.println(responseEntity);
        assertNotNull("Resulting ResponseEntity was empty", responseEntity);
        assertThat(responseEntity.getStatusCode(), equalTo(CREATED));
    }
}
