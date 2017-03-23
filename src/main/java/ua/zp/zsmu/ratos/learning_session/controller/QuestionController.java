package ua.zp.zsmu.ratos.learning_session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.zp.zsmu.ratos.learning_session.service.QuestionService;

/**
 * Created by Andrey on 23.03.2017.
 */
@RestController
public class QuestionController {
        @Autowired
        private QuestionService questionService;

        @GetMapping("/")
        public String start() {
                return "Hello!";
        }

        @GetMapping("/firstQuestion")
        public String findOne(@RequestParam Long id) {
                return questionService.findOne(id).toString();
        }
}
