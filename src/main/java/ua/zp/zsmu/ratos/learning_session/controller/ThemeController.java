package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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

        @GetMapping("/findTheme/query")
        @ResponseBody
        public String findOneWithQuery(@RequestParam Long id) {
                Theme theme = themeService.findOneThemeWithQuestions(id);
                LOGGER.info("FindTheme: "+theme);
                try {
                        LOGGER.info("Questions: "+theme.getQuestions());
                } catch (Exception e) {
                        LOGGER.error("ERROR: "+e.getMessage()+" CAUSE: "+e.getCause());
                }
                return theme.toString();
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
                modelAndView.setViewName("question");
                modelAndView.addObject(theme);
                return modelAndView;
        }

        @GetMapping("/th/findTheme/jpa")
        public ModelAndView findOneJPA(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView();
                long start = System.nanoTime();
                Theme theme = themeService.findOneThemeWithQuestionsJPA(id);
                long finish = System.nanoTime();
                LOGGER.info("EntityManager took: "+(finish-start)/1000000+" ms");
                modelAndView.setViewName("question");
                modelAndView.addObject(theme);
                return modelAndView;
        }



        @GetMapping("/findAllThemes")
        @ResponseBody
        public ResponseEntity<List<Theme>> findAll() {
                return new ResponseEntity<List<Theme>>(themeService.findAll(), HttpStatus.OK);
        }
}
