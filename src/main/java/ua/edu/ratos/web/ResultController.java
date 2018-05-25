package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.domain.model.Result;
import ua.edu.ratos.service.ResultService;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class ResultController {

    @Autowired
    private ResultService resultService;

    @RequestMapping("/results")
    public List<Result> results() {
        return  resultService.findAll();
    }

    @RequestMapping("/result")
    public Result result() {
        return  resultService.findOne(1);
    }

    @RequestMapping("/resultString")
    public String resultString() {
        return  resultService.findOne(1).getUser();
    }

    @RequestMapping("/test")
    public String resultsTest() {
        RestTemplate restTemplate = new RestTemplate();
        Result body = restTemplate.getForObject("http://localhost:8080/result", Result.class);
        return body.getUser();
    }

    @RequestMapping("/exception")
    public void exceptionTest() {
        log.error("Some error {}", " in ResultController");
    }

}
