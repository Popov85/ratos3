package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import ua.zp.zsmu.ratos.learning_session.dao.SessionDAO;
import ua.zp.zsmu.ratos.learning_session.model.Session;
import ua.zp.zsmu.ratos.learning_session.service.ISession;
import ua.zp.zsmu.ratos.learning_session.service.RandomSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Andrey on 4/8/2017.
 */
@Controller
//@Scope("prototype")
public class SessionController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SessionController.class);

        @Autowired
        private SessionDAO sessionDAO;

        /*@Autowired
        private ISession session;*/

        @GetMapping("/findOneSession")
        @ResponseBody
        public ResponseEntity<Session> findOneSession(@RequestParam Long id) {
                return new ResponseEntity<Session>(sessionDAO.findOne(id), HttpStatus.OK);
        }

        @GetMapping("/startNewSession")
        @ResponseBody
        public String startNewSession(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
                Cookie[] cookies = request.getCookies();
                if (cookies.length!=0) {
                        for (Cookie cookie : cookies) {
                                LOGGER.info("Name: "+cookie.getName()+" Value: "+cookie.getValue());
                        }
                }
                //response.addCookie(new Cookie("COOKIENAME", "The cookie's value"));
                if (!session.isNew()) return "We already created session for you!";
                ISession iSession = new RandomSession();
                session.setAttribute("s", iSession);
                //session.setMaxInactiveInterval(30);
                LOGGER.info("ISessionIs: "+iSession.hashCode());
                LOGGER.info("ControllerIs: "+this.hashCode());
                return Integer.toString(iSession.hashCode());
        }

        @GetMapping("/provideNextQuestion")
        @ResponseBody
        public String provideNextQuestion(HttpSession session) {
                // Check if we already have smth. in session
                if (session.isNew()) return "New";
                ISession iSession = (ISession) session.getAttribute("s");
                LOGGER.info("ISessionIs: "+iSession.hashCode());
                LOGGER.info("ControllerIs: "+this.hashCode());
                return Integer.toString(iSession.hashCode());
        }

        @GetMapping("/finishSession")
        @ResponseBody
        public String finishSession(HttpSession session) {
                session.invalidate();
                return "Finished";
        }
}
