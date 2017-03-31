package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.zp.zsmu.ratos.learning_session.dao.SchemeDAO;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

import java.util.List;

/**
 * Created by Andrey on 31.03.2017.
 */
public class SchemeService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SchemeService.class);

        @Autowired
        private SchemeDAO schemeDAO;

        public List<Scheme> findAll() {
                return schemeDAO.findAll();
        }

        public Scheme findOne(Long id) {
                LOGGER.info("findOne: " + schemeDAO.findOne(id));
                return schemeDAO.findOne(id);
        }
}
