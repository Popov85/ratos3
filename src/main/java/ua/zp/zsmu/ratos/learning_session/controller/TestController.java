package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.dao.QuestionDAO;
import ua.zp.zsmu.ratos.learning_session.dao.ThemeDAO;
import ua.zp.zsmu.ratos.learning_session.model.Answer;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.Student;
import ua.zp.zsmu.ratos.learning_session.service.dto.AnswerDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.QuestionDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.ResultDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.SchemeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 14.04.2017.
 */
@Controller
public class TestController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(TestController.class);

        @Autowired
        private ThemeDAO themeDAO;

        @Autowired
        private QuestionDAO questionDAO;

        @GetMapping("/test")
        @ResponseBody
        private String start() {
                Theme dbTest = themeDAO.findOne(1411l);
                LOGGER.info("Theme: "+dbTest);
                return dbTest.toString();
        }

        @GetMapping("/test/question")
        private ModelAndView question() {
                ModelAndView modelAndView = new ModelAndView("question");
                Question q = questionDAO.findOneQuestionWithAnswers(130608L);
                QuestionDTO qDTO = new QuestionDTO(
                        q.getId(),
                        q.getTitle(),
                        createAnswersDTO(q.getAnswers()),
                        createSchemeDTO(),
                        new Student(),
                        12365232L,
                        20,
                        50d
                );
                modelAndView.addObject("question", qDTO);
                return modelAndView;
        }

        private SchemeDTO createSchemeDTO() {
                SchemeDTO s = new SchemeDTO(1L, "Ботаника", "*");
                return s;
        }

        private List<AnswerDTO> createAnswersDTO(List<Answer> answers) {
                List<AnswerDTO> result = new ArrayList<>();
                for (Answer answer : answers) {
                        AnswerDTO a = new AnswerDTO(answer.getId(), answer.getTitle());
                        result.add(a);
                }
                return result;
        }

        @GetMapping("/test/result")
        private ModelAndView result() {
                ModelAndView modelAndView = new ModelAndView("result");
                Student student = new Student();
                student.setName("Андрій");
                student.setSurname("Попов");
                student.setCourse("2");
                student.setGroup("25");
                student.setFaculty("Фармацевтичний");
                ResultDTO result = new ResultDTO(111111111L, student, "Схема #1", 75.5d, "5");
                modelAndView.addObject("result", result);
                return modelAndView;
        }
                  /*       LOGGER.info("all params: ");
                        Map<String, String[]> params =  request.getParameterMap();
                        for (Map.Entry<String, String[]> stringEntry : params.entrySet()) {
                                LOGGER.info("key: "+stringEntry.getKey());
                                LOGGER.info("value: "+stringEntry.getValue());
                                if (stringEntry.getValue().length>1) {
                                        LOGGER.info("many values");
                                        String[] values = stringEntry.getValue();
                                        for (String value : values) {
                                                LOGGER.info("value="+value);
                                        }
                                }
                        }*/
}
