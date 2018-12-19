package ua.edu.ratos.web.lti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/strings")
    @ResponseBody
    public List<String> strings() {
        return Arrays.asList("String 1","String 2", "String 3");
    }

    @GetMapping("/student/test")
    @ResponseBody
    public String student() {
        return "OK (student)";
    }

    @GetMapping("/instructor/test")
    @ResponseBody
    public String instructor() {
        return "OK (instructor)";
    }
}
