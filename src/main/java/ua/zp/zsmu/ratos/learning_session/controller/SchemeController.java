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
import ua.zp.zsmu.ratos.learning_session.dao.SchemeDAO;
import ua.zp.zsmu.ratos.learning_session.dao.SchemeThemeDAO;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.SchemeTheme;
import ua.zp.zsmu.ratos.learning_session.service.SchemeService;

/**
 * Created by Andrey on 31.03.2017.
 */
@Controller
public class SchemeController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SchemeController.class);

        @Autowired
        private SchemeService schemeService;

        @Autowired
        private SchemeDAO schemeDAO;

        @Autowired
        private SchemeThemeDAO schemeThemeDAO;

        @GetMapping("/findOneScheme")
        @ResponseBody
        public ResponseEntity<Scheme> findOne(@RequestParam Long id) {
                try {
                        LOGGER.info("findOne: "+schemeService.findOne(id).toString());
                } catch (Exception e) {
                        LOGGER.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                }
                return new ResponseEntity<Scheme>(schemeService.findOne(id), HttpStatus.OK);
        }

        @GetMapping("/findOneSchemeWithThemes")
        @ResponseBody
        public ResponseEntity<Scheme> findOneWithThemes(@RequestParam Long id) {
                try {
                        LOGGER.info("SchemeWithThemes: "+schemeDAO.findOneWithThemes(id));
                        return new ResponseEntity<Scheme>(schemeDAO.findOneWithThemes(id), HttpStatus.OK);
                } catch (Exception e) {
                        LOGGER.error("ERROR: "+e.getMessage()+" cause: "+e.getClass()+" trace:"+e.getStackTrace());
                        return new ResponseEntity<Scheme>(HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping("/findOneSchemeTheme")
        @ResponseBody
        public ResponseEntity<SchemeTheme> findOneSchemeTheme(@RequestParam Long id) {
                try {
                        LOGGER.info("SchemeTheme: "+schemeThemeDAO.findOne(id));
                        return new ResponseEntity<SchemeTheme>(schemeThemeDAO.findOne(id), HttpStatus.OK);
                } catch (Exception e) {
                        LOGGER.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                        return new ResponseEntity<SchemeTheme>(HttpStatus.BAD_REQUEST);
                }
        }


}
