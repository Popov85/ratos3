package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.zp.zsmu.ratos.learning_session.dao.FacultyDAO;
import ua.zp.zsmu.ratos.learning_session.model.Faculty;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.service.*;
import ua.zp.zsmu.ratos.learning_session.service.dto.QuestionDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.ResultDTO;
import ua.zp.zsmu.ratos.learning_session.service.dto.SchemeDTO;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.LostSessionException;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.QuestionAlreadyAnsweredException;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.TimeIsOverException;
import ua.zp.zsmu.ratos.learning_session.service.exceptions.UnsupportedQuestionTypeException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        @Autowired
        private FacultyDAO facultyDAO;

  /*      @GetMapping("/findOneSession")
        @ResponseBody
        public ResponseEntity<Session> findOneSession(@RequestParam Long id) {
                return new ResponseEntity<Session>(sessionService.findOne(id), HttpStatus.OK);
        }*/

        @GetMapping("/ratos/start")
        public ModelAndView index(HttpServletRequest request) {
                String ip = request.getRemoteAddr();
                Map<String, Object> model = new HashMap<>();
                model.put("ip", ip);
                List<Faculty> faculties = facultyDAO.findAll();
                LOGGER.info("faculties: "+faculties);
                model.put("faculties", faculties);
                List<SchemeDTO> schemes = schemeService.findAllAvailableFromIPAddress("192.168.1.140");
                LOGGER.info("schemes: "+schemes);
                model.put("schemes", schemes);
                model.put("student", new Student());
                return new ModelAndView("start", model);
        }

        @GetMapping("ratos/interrupt")
        public String interruption() {
                return "interruption";
        }

        @GetMapping("/result")
        public String result() {
                return "result";
        }

        @PostMapping("/ratos/start")
        @ResponseBody
        public ModelAndView startSession(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                         @Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
                                         Scheme scheme) {
                ModelAndView modelAndView = new ModelAndView();
                if (bindingResult.hasErrors()) {
                        LOGGER.error("# of errors is: "+bindingResult.getFieldErrorCount());
                        modelAndView.setViewName("start");
                        return modelAndView;
                }
                //modelAndView.setViewName("question");
                LOGGER.info("Student: "+student);
                LOGGER.info("Scheme: "+scheme);
                // WARN! Prevent two different session to launch from one PC!
                // If we already have SID in cookies - we must check the possibility to continue the previous session
                // If session was already opened do not launch "start" - just continue with next question
                if (!session.isNew()) {
                        modelAndView.setViewName("redirect:/ratos/interrupt");
                        return modelAndView;
                }

                // 0. Instantiate Student (by all fields) and Scheme objects (by selected id)

                // Normally SID should be absent in cookies. If not,
                // then a session was interrupted due the problems with server (sudden stop e.g.)
                // In this case, we should try to retrieve the saved session from DB and continue (return next question)

                // 1. Launch start at SessionService
                ISession iSession = null;
                try {
                        iSession = sessionService.start(student, scheme);
                } catch (Exception e) {
                        // Catch out of memory exception
                        LOGGER.error("ERROR: "+e.getMessage());
                        modelAndView.setViewName("redirect:/error");
                        return modelAndView;
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
                return getQuestion(iSession, modelAndView, true);
        }

        private ModelAndView getQuestion(ISession iSession, ModelAndView modelAndView, boolean state) {
                QuestionDTO question = null;
                try {
                        question = sessionService.provideNextQuestion(iSession, state);
                        modelAndView.setViewName("question");
                        modelAndView.addObject("question", question);
                } catch (TimeIsOverException e) {
                        ResultDTO result = iSession.interruptSessionByTimeout();
                        // return result.html
                        modelAndView.setViewName("redirect:/result");
                        modelAndView.addObject("result", result);
                        return modelAndView;
                } catch (UnsupportedQuestionTypeException e) {
                        // return empty question with
                        modelAndView.setViewName("question");
                        modelAndView.addObject("message", "Unsupported question type!");
                        return modelAndView;
                }
                return modelAndView;
        }

        @PostMapping("/answer")
        @ResponseBody
        public String provideAnswer(Long qid, HttpSession session) {
                // TODO
                return "";
        }

        @PostMapping("/ratos/next")
        public ModelAndView provideNextQuestion(HttpSession session, HttpServletRequest request, ModelAndView modelAndView,
                                                @RequestParam(value = "qid", required=false) Long qid,
                                                @RequestParam(value = "answer", required=false) List<Long> answer
                                                ) throws QuestionAlreadyAnsweredException {
                // Check if we already have smth. in session
                ISession iSession = null;
                QuestionDTO question = null;
                try {
                        iSession = getSession(session, request);

                        LOGGER.info("qid: "+qid);
                        LOGGER.info("answer: "+answer);

                        if (iSession.isQuestionsRunOut()) {
                                ResultDTO result = iSession.finishSession();
                                modelAndView.setViewName("result");
                                modelAndView.addObject("result", result);
                                return modelAndView;
                        }
                        if (qid==null) {
                                // provide same question one more time
                                modelAndView = getQuestion(iSession, modelAndView, false);
                        } else {
                                // process response and provide next question
                                iSession.processStudentAnswer(qid, answer);
                                modelAndView = getQuestion(iSession, modelAndView, true);
                        }
                } catch (LostSessionException e) {
                        LOGGER.error("Cannot find session: possible reasons" +
                                " 1) cookies JSESSIONID and SID were deleted" +
                                " 2) Server's sudden unavailability");
                        // No way to produce result, sorry
                        modelAndView.setViewName("error");
                        modelAndView.addObject("message", "Error page with the possibility to return to start page");
                        return modelAndView;
                }
                return modelAndView;
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
                return "redirect:/ratos/start";
        }

        @PostMapping("/ratos/finish")
        public String finishSession(HttpSession session) {
                session.invalidate();
                return "redirect:/ratos/start";
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
