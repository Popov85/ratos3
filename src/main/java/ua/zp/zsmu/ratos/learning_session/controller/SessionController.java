package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.model.Session;
import ua.zp.zsmu.ratos.learning_session.service.*;
import ua.zp.zsmu.ratos.learning_session.service.dto.QuestionDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.ResultDTO;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.LostSessionException;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.QuestionAlreadyAnsweredException;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.TimeIsOverException;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.UnsupportedQuestionTypeException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Andrey on 4/8/2017.
 */
@Controller
public class SessionController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SessionController.class);

        @Autowired
        private SessionService sessionService;

        @Autowired
        private SchemeService schemeService;

        @GetMapping("/findOneSession")
        @ResponseBody
        public ResponseEntity<Session> findOneSession(@RequestParam Long id) {
                return new ResponseEntity<Session>(sessionService.findOne(id), HttpStatus.OK);
        }

        @GetMapping("/index")
        public ModelAndView index() {
                // 1. Fill faculties, courses and specialisations
                // 2. Fill schemes (filter by IP)
                // 3. Return view
                return new ModelAndView();
        }

        // @PostMapping
        @GetMapping("/start")
        @ResponseBody
        public String startSession(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

                // WARN! Prevent two different session to launch from one PC!
                // If we already have SID in cookies - we must check the possibility to continue the previous session
                // If session was already opened do not launch "start" - just continue with next question
                if (!session.isNew()) return "We already created a session for you!";

                // 0. Instantiate Student (by all fields) and Scheme objects (by selected id)

                // Normally SID should be absent in cookies. If not,
                // then a session was interrupted due the problems with server (sudden stop e.g.)
                // In this case, we should try to retrieve the saved session from DB and continue (return next question)

                // 1. Launch start at SessionService
                ISession iSession = null;
                try {
                        iSession = sessionService.start(new Student(), schemeService.findOne(53l));
                } catch (Exception e) {
                        // Catch out of memory exception
                        LOGGER.error("ERROR: "+e.getMessage());
                        return "Error";
                }
                // 2. Save the produced ISession to a session variable
                session.setAttribute("session", iSession);
                // 2.1. From produced ISession retrieve sid and send it to cookies
                Cookie cookie = new Cookie("SID", Long.toString(iSession.getStoredSessionID()));
                // Never expire by time, let the user do it.
                //cookie.setMaxAge(60*60);
                cookie.setSecure(true);
                cookie.isHttpOnly();
                response.addCookie(cookie);
                // 3. Obtained ISession use to return first question
                QuestionDTO question = null; //iSession.provideNextQuestion();
                try {
                        question = sessionService.provideNextQuestion(iSession);
                } catch (TimeIsOverException e) {
                        ResultDTO result = iSession.interruptSessionByTimeout();
                        // return result.html
                        return "redirect:";
                } catch (UnsupportedQuestionTypeException e) {
                        // return empty question with
                        return "Unsupported question type!";
                }
                // 3.1 Add Question to model
                // 4. Return model and view
                return question.toString();
        }

        @PostMapping("/answer")
        @ResponseBody
        public String provideAnswer(Long qid, HttpSession session) {

                return "";
        }

        // @PostMapping!!!
        @GetMapping("/next")
        @ResponseBody
        public String provideNextQuestion(HttpSession session, HttpServletRequest request) throws QuestionAlreadyAnsweredException {
                // Check if we already have smth. in session
                ISession iSession = null;
                QuestionDTO question = null;
                try {
                        iSession = getSession(session, request);
                        // empty list (ONLY for test purposes)
                        iSession.processStudentAnswer(1L, new ArrayList<>());
                        question = sessionService.provideNextQuestion(iSession);
                } catch (LostSessionException e) {
                        LOGGER.error("Cannot find session: possible reasons" +
                                " 1) cookies JSESSIONID and SID were deleted" +
                                " 2) Server's sudden unavailability");
                        // No way to produce result, sorry
                        return "redirect: Error page with the possibility to return to start page";
                } catch (TimeIsOverException e) {
                        ResultDTO result = iSession.interruptSessionByTimeout();
                        // return result.html
                        return "redirect: result page";
                } catch (UnsupportedQuestionTypeException e) {
                        // return empty question with
                        return "Unsupported question type!";
                }
                return question+"";
        }

        // called by JS when it is required by session settings
        @PostMapping("/right")
        @ResponseBody
        public String provideAnswer(HttpSession session, HttpServletRequest request) {
                try {
                        ISession iSession = getSession(session, request);
                        iSession.provideAnswers();
                } catch (LostSessionException e) {
                        LOGGER.error("ERROR at provideAnswer() "+e.getMessage());
                        return "redirect: Error page with the possibility to return to start page";
                } catch (Exception e) {
                        LOGGER.error("An unexpected exception at provideAnswer(): "+e.getMessage());
                        return "error message";
                }
                return "answers";
        }

        @PostMapping("/skip")
        @ResponseBody
        public String skipQuestion(HttpSession session, HttpServletRequest request) {
                ISession iSession = null;
                try {
                        iSession = getSession(session, request);
                        iSession.skipQuestion();
                } catch (LostSessionException e) {
                        LOGGER.error("Cannot find session: possible reasons" +
                                " 1) cookies JSESSIONID and SID were deleted" +
                                " 2) Server's sudden unavailability");
                        return "redirect: Error page with the possibility to return to start page";
                } catch (TimeIsOverException e) {
                        ResultDTO result = iSession.interruptSessionByTimeout();
                        // return result.html
                        return "redirect: result page";
                }
                return "Skipped";
        }

        @PostMapping("/help")
        @ResponseBody
        public String provideHelp(HttpSession session, HttpServletRequest request) {
                String help = "";
                try {
                        ISession iSession = getSession(session, request);
                        help = iSession.provideHelp();
                } catch (LostSessionException e) {
                        e.printStackTrace();
                } catch (TimeIsOverException e) {
                        e.printStackTrace();
                }
                return help;
        }

        @PostMapping("/hint")
        @ResponseBody
        public String provideHint(HttpSession session, HttpServletRequest request) {
                String hint = "";
                try {
                        ISession iSession = getSession(session, request);
                        hint = iSession.provideHint();
                } catch (LostSessionException e) {
                        e.printStackTrace();
                } catch (TimeIsOverException e) {
                        e.printStackTrace();
                }
                return hint;
        }

        @GetMapping("/finish")
        public String finish(HttpSession session) {
                session.invalidate();
                return "";
        }

        @PostMapping("/finish")
        @ResponseBody
        public String finishSession(HttpSession session) {
                session.invalidate();
                return "Finished";
        }

        private ISession getSession(HttpSession session, HttpServletRequest request) throws LostSessionException {
                ISession iSession = null;
                try {
                        iSession = (ISession) session.getAttribute("session");
                        if (iSession==null) {
                                iSession = sessionService.restore(Long.valueOf(getSIDCookie(request)));
                        }
                } catch (Exception e) {
                        LOGGER.error("ERROR at getSession(): "+e.getMessage());
                        throw new LostSessionException("Lost your session and failed to restore it from DB!");
                }
                return iSession;
        }

        private String getSIDCookie(HttpServletRequest request) {
                String value = "";
                for (Cookie c : request.getCookies()) {
                        if (c.getName().equals("SID"))
                                value = c.getValue();
                }
                return value;
        }

}
