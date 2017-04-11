package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Session;
import ua.zp.zsmu.ratos.learning_session.service.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        public String startNewSession(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

                // If session was already opened do not launch "start" - just continue with next question
                if (!session.isNew()) return "We already created session for you!";

                // 0. Instantiate Student (by all fields) and Scheme objects (by selected id)

                // WARN! Prevent two different session to launch from one PC!
                // If we already have SID in cookies - we must check the possibility to continue the previous session

                // Normally SID should be absent in cookies. If not,
                // then a session was interrupted due the problems with server (sudden stop e.g.)
                // In this case, we should try to retrieve the saved session from DB and continue (return next question)

                // 1. Launch start at SessionService
                ISession iSession = null;
                try {
                        iSession = sessionService.start(new Student(), schemeService.findOne(53l));
                } catch (Exception e) {
                        LOGGER.error("ERROR: "+e.getMessage());
                        return "Error";
                }
                // 2. Save the produced ISession to a session variable
                session.setAttribute("session", iSession);
                // 2.1. From produced ISession retrieve sid and send it to cookies
                Cookie cookie = new Cookie("SID", Long.toString(123));
                //response.addCookie(new Cookie("SID", Long.toString(iSession.getStoredSessionID())));
                // Calculate TODO
                cookie.setMaxAge(60*60);
                cookie.setSecure(true);
                cookie.isHttpOnly();
                response.addCookie(cookie);
                // 3. Obtained ISession use to return first question
                Question question = iSession.provideNextQuestion();
                // 3.1 Add Question to model
                // 4. Return model and view
                LOGGER.info("QuestionIs: "+question);
                //session.setMaxInactiveInterval(30);
                LOGGER.info("ISessionIs: "+iSession.hashCode());
                LOGGER.info("ControllerIs: "+this.hashCode());
                return Integer.toString(iSession.hashCode())+question;
        }

        private boolean isInterrupted(Cookie[] cookies) {
                // If current JSESSIONID is not associated with any object
                if (cookies.length!=0) {
                        for (Cookie cookie : cookies) {
                                if (cookie.getName().equals("SID")) return true;
                                LOGGER.info("Name: "+cookie.getName()+" Value: "+cookie.getValue());
                        }
                }
                return false;
        }


        @GetMapping("/next")
        @ResponseBody
        public String provideNextQuestion(HttpSession session) {
                        /*if (isInterrupted(request.getCookies())) {
                        // Retrieve existing ISession from DB
                        // Repeat steps to produce next question
                }*/
                // Check if we already have smth. in session
                if (session.isNew()) return "New";
                ISession iSession = (ISession) session.getAttribute("s");
                LOGGER.info("ISessionIs: "+iSession.hashCode());
                LOGGER.info("ControllerIs: "+this.hashCode());
                return Integer.toString(iSession.hashCode());
        }

        @GetMapping("/finish")
        @ResponseBody
        public String finishSession(HttpSession session) {
                session.invalidate();
                return "Finished";
        }
}
