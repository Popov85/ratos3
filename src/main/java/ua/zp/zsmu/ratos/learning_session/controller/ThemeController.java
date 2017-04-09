package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.app_config.HibernateAwareObjectMapper;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.ThemeService;

import java.util.List;

/**
 * Created by Andrey on 03.04.2017.
 */
@Controller
public class ThemeController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ThemeController.class);

        @Autowired
        private ThemeService themeService;

        @GetMapping("/th/findTheme/query")
        @ResponseBody
        public ModelAndView findOneWithQuery(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView();
                long start = System.nanoTime();
                Theme theme = themeService.findOneWithQuestions(id);
                long finish = System.nanoTime();
                LOGGER.info("QUERY took: "+(finish-start)/1000000+" ms");
                modelAndView.setViewName("index");
                modelAndView.addObject(theme);
                return modelAndView;
        }

        @GetMapping("/findThemeQuestions/jdbc")
        @ResponseBody
        public List<Question> findThemeQuestions(@RequestParam Long id) {
                List<Question> questions = null;
                try {
                        long start = System.nanoTime();
                        questions = themeService.findQuestionsByTheme(id);
                        long finish = System.nanoTime();
                        LOGGER.info("JDBC took: "+(finish-start)/1000000+" ms");
                        //LOGGER.info("FindThemeQuestions: "+questions);
                } catch (Exception e) {
                        LOGGER.error("ERROR: "+e.getMessage()+" CAUSE: "+e.getCause());
                }
                return questions;
        }

        @GetMapping("/th/findTheme/data")
        public ModelAndView findOneData(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView();
                long start = System.nanoTime();
                Theme theme = themeService.findOne(id);
                long finish = System.nanoTime();
                LOGGER.info("DATA took: "+(finish-start)/1000000+" ms");
                modelAndView.setViewName("index");
                modelAndView.addObject(theme);
                return modelAndView;
        }

        @GetMapping("/th/findAllTitles/data")
        @ResponseBody
        public String findAllThemeTitles() {
                List<Theme> themes = null;
                String result = null;
                try {
                        long start = System.nanoTime();
                        //List<String> theme = themeService.findAllThemeTitles();
                        themes = themeService.findAll();
                        long finish = System.nanoTime();
                        LOGGER.info("Request took: "+(finish-start)/1000000+" ms");
                        ObjectMapper objectMapper = new HibernateAwareObjectMapper();
                        result = objectMapper.writeValueAsString(themes);
                } catch (Exception e) {
                        LOGGER.error("ERR at controller: "+e.getMessage());
                }
                return result;
        }

        @GetMapping("/th/findTheme/jpa")
        public ModelAndView findOneJPA(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView();
                long start = System.nanoTime();
                Theme theme = themeService.findOneThemeWithQuestionsJPA(id);
                long finish = System.nanoTime();
                LOGGER.info("EntityManager took: "+(finish-start)/1000000+" ms");
                modelAndView.setViewName("index");
                modelAndView.addObject(theme);
                return modelAndView;
        }

        @GetMapping("/findAllThemes")
        @ResponseBody
        public ResponseEntity<List<Theme>> findAll() {
                return new ResponseEntity<List<Theme>>(themeService.findAll(), HttpStatus.OK);
        }

        @GetMapping("/findOneWithQuestions")
        @ResponseBody
        public ResponseEntity<Theme> findOneWithQ(@RequestParam Long id) {
                return new ResponseEntity<Theme>(themeService.findOneWithQuestions(id), HttpStatus.OK);
        }
}
