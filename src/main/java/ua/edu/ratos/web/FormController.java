package ua.edu.ratos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping("/question/form")
    public String getForm() {
        return "form";
    }
}
