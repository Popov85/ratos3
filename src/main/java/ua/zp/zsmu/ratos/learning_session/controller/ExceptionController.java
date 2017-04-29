package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.QuestionAlreadyAnsweredException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Andrey on 27.04.2017.
 */
@ControllerAdvice
public class ExceptionController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ExceptionController.class);

        private static final String REPEATED_ANSWER_MESSAGE = "You have already provided answer to this question";

        // Total control - setup a model and return the view name yourself. Or
        // consider subclassing ExceptionHandlerExceptionResolver (see below).
        @ExceptionHandler(Exception.class)
        public ModelAndView handleError(HttpServletRequest req, Exception ex) {
                LOGGER.error("Request: " + req.getRequestURL() + " raised " + ex);
                ModelAndView mav = new ModelAndView();
                mav.addObject("exception", ex);
                mav.addObject("url", req.getRequestURL());
                mav.setViewName("error");
                return mav;
        }

        @ExceptionHandler(QuestionAlreadyAnsweredException.class)
        @ResponseBody
        public String handleRepeatedAnswerError(Exception ex, WebRequest request) {
                LOGGER.info("Advice in use!");
                ModelMap map = new ModelMap();
                map.addAttribute("message", REPEATED_ANSWER_MESSAGE);
                //model.addAttribute("message", REPEATED_ANSWER_MESSAGE);
                return REPEATED_ANSWER_MESSAGE;
        }
}
