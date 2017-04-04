package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.service.QuestionService;

/**
 * Created by Andrey on 23.03.2017.
 */
@Controller
public class QuestionController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(QuestionController.class);

        @Autowired
        private QuestionService questionService;

        @GetMapping("/")
        @ResponseBody
        public String start() {
                return "Hello!";
        }

        @GetMapping("/firstQuestion")
        @ResponseBody
        public String findOne(@RequestParam Long id) {
                return questionService.findOne(id).toString();
        }

        @GetMapping("/th/firstQuestion")
        public ModelAndView findOneObject(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView();
                Question question = questionService.findOne(id);
                modelAndView.setViewName("question");
                modelAndView.addObject(question);
                return modelAndView;
        }
}
