package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.domain.model.ResultMock;
import ua.edu.ratos.service.ResultService;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class ResultController {

    @Autowired
    private ResultService resultService;

    @RequestMapping("/results")
    public List<ResultMock> results() {
        return  resultService.findAll();
    }

    @RequestMapping("/result")
    public ResultMock result() {
        return  resultService.findOne(1);
    }

    @RequestMapping("/resultString")
    public String resultString() {
        return  resultService.findOne(1).getUser();
    }

    @RequestMapping("/test")
    public String resultsTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResultMock body = restTemplate.getForObject("http://localhost:8080/result", ResultMock.class);
        return body.getUser();
    }

    @RequestMapping("/exception")
    public void exceptionTest() {
        log.error("Some error {}", " in ResultController");
    }

}
