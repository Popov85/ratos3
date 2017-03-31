package ua.zp.zsmu.ratos.learning_session.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.zp.zsmu.ratos.learning_session.service.SchemeService;

/**
 * Created by Andrey on 31.03.2017.
 */
@Controller
public class SchemeController {
        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SchemeController.class);

        @Autowired
        private SchemeService schemeService;

        @GetMapping("/firstScheme")
        public String findOne(@RequestParam Long id) {
                return schemeService.findOne(id).toString();
        }

}
