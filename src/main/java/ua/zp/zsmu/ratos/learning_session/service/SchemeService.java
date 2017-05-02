package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.ComputerDAO;
import ua.zp.zsmu.ratos.learning_session.dao.SchemeDAO;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.service.dto.SchemeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 31.03.2017.
 */
@Transactional
public class SchemeService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SchemeService.class);

        @Autowired
        private SchemeDAO schemeDAO;

        @Autowired
        private ComputerDAO computerDAO;

        @Autowired
        private IPChecker ipChecker;

        public List<Scheme> findAll() {
                return schemeDAO.findAll();
        }

        public Scheme findOne(Long id) {
                return schemeDAO.findOne(id);
        }

        public Scheme findOneWithThemes(Long id) {
                return schemeDAO.findOneWithThemes(id);
        }

        /**
         * Filters list of all existing schemes based on a remote IP-address
         * @param remoteIP - IP-address from which the request was sent
         * @return list of schemes available from this IP-address
         */

        public List<SchemeDTO> findAllAvailableFromIPAddress(String remoteIP) {
                List<Integer> classRooms = getClassRoomsIPBelongs(remoteIP);
                List<Scheme> allSchemes = schemeDAO.findAllAvailableSchemes();
                List<SchemeDTO> availableSchemes = new ArrayList<>();
                for (Scheme scheme : allSchemes) {
                        String mask = scheme.getClassRooms();
                        if (ipChecker.isSchemeAvailableFromIPAddress(mask, remoteIP, classRooms))
                                availableSchemes.add(new SchemeDTO(scheme.getId(),
                                        scheme.getTitle(), scheme.getMaskIPAddress()));
                }
                return availableSchemes;
        }

        private List<Integer> getClassRoomsIPBelongs(String remoteIP) {
                List<Integer> classRooms = computerDAO.findAllClassRoomIDsByIP(remoteIP);
                LOGGER.info("classRooms: "+classRooms);
                return classRooms;
        }
}
