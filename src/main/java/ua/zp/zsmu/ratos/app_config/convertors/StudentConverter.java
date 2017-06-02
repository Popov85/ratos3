package ua.zp.zsmu.ratos.app_config.convertors;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ua.zp.zsmu.ratos.learning_session.service.Student;

/**
 * Created by Andrey on 5/2/2017.
 */
public class StudentConverter implements HandlerMethodArgumentResolver {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(StudentConverter.class);

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
                LOGGER.info("invoked... equals");
                return parameter.getParameterType().equals(Student.class);
        }

        @Override
        public Object resolveArgument(MethodParameter methodParameter,
                                      ModelAndViewContainer modelAndViewContainer,
                                      NativeWebRequest nativeWebRequest,
                                      WebDataBinderFactory webDataBinderFactory) throws Exception {
           LOGGER.info("invoked...");

            /*
                String name = nativeWebRequest.getParameter("name");
                LOGGER.info("myname= "+name);
                String surname = nativeWebRequest.getParameter("surname");
                LOGGER.info("mysurname= "+surname);
                String group = nativeWebRequest.getParameter("group");
                String faculty = nativeWebRequest.getParameter("faculty");
                String course = nativeWebRequest.getParameter("course");
                Student student = new Student(name, surname, group, faculty, course);*/
                return new Object();
        }


}
