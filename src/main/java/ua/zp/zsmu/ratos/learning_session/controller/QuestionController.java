package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.QuestionService;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Andrey on 23.03.2017.
 */
@Controller
public class QuestionController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(QuestionController.class);

        @Autowired
        private QuestionService questionService;

        /*@GetMapping("/")
        public String start() {
                return "Hello!";
        }

        @GetMapping("/firstQuestion")
        public String findOne(@RequestParam Long id) {
                return questionService.findOne(id).toString();
        }*/

        @GetMapping("/firstQuestion/object")
        public ModelAndView findOneObject(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView();
                Question question = questionService.findOne(id);
                Theme theme = new Theme();
                theme.setId(1l);
                theme.setCourse(2l);
                theme.setTitle("Белки и углеводы");
                LOGGER.info("findOneObject: "+theme);
                modelAndView.setViewName("question");
                modelAndView.addObject(question);
                modelAndView.addObject(theme);
                return modelAndView;
        }
}
