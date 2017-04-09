package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.service.QuestionService;

import java.util.List;

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

        @GetMapping("/th/findQuestion/data")
        public ModelAndView findOneData(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView();
                Question question = questionService.findOne(id);
                modelAndView.setViewName("index");
                modelAndView.addObject(question);
                return modelAndView;
        }

        @GetMapping("/th/findQuestion/query")
        public ModelAndView findOneQuery(@RequestParam Long id) throws JsonProcessingException {
                ModelAndView modelAndView = new ModelAndView();
                Question question = questionService.findOneWithAnswers(id);
                LOGGER.info("Answers: "+question.getAnswers());
                modelAndView.setViewName("index");
                modelAndView.addObject(question);
                return modelAndView;
        }

        @GetMapping("/th/findQuestion1/query")
        @ResponseBody
        public ResponseEntity<Question> findOne1Query(@RequestParam Long id) throws JsonProcessingException {
                Question question = questionService.findOneWithAnswers(id);
                return new ResponseEntity<Question>(question, HttpStatus.OK);
        }

        @GetMapping("/findQuestionsByTheme/nativeQuery")
        @ResponseBody
        public ResponseEntity<List<Question>> findQuestionsByTheme(@RequestParam Long id) {
                List<Question> questions = questionService.findNRandomByTheme(id, 10);
                return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
        }
}
