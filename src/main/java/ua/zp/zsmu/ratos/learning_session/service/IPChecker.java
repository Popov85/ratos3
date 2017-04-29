package ua.zp.zsmu.ratos.learning_session.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zp.zsmu.ratos.learning_session.dao.ClassRoomDAO;
import ua.zp.zsmu.ratos.learning_session.model.ClassRoom;
import ua.zp.zsmu.ratos.learning_session.model.Computer;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

import java.util.List;

/**
 * Checks if a given scheme is available from a remote IP-address
 * Created by Andrey on 28.04.2017.
 */
@Service
public class IPChecker {

        @Autowired
        private ClassRoomDAO classDAO;

        /**
         * A scheme contains a string separated by semicolon (;) which specifies classes,
         * IP-addresses and IP-masks from which the scheme is available
         * e.g. :
         * 1) * - available for everybody
         * 2) 14;15;25;33 - available for computer classes with  id=14, id = 15 etc.
         * 3) 192.168.* - available for the whole mask
         * 4) 192.168.70.140; 192.168.70.141; 192.168.70.142 - available only for these 3 specified IP-addresses
         * 5) 14;25;192.168.70.*; 192.168.1.1 - available for 2 comp. classes, mask and one specified IP-address
         * @param scheme
         * @param remoteIP
         * @return
         */
        public boolean isSchemeAvailableFromIPAddress(Scheme scheme, String remoteIP) {
                String mask = scheme.getMaskIPAddress();
                if (isAvailableForAll(mask)) return true;
                // get an array of
                String[] addresses = mask.split(";");
                for (String address : addresses) {
                        if (isAvailableForSubMask(address, remoteIP)) return true;
                }
                return false;
        }

        private boolean isAvailableForAll(String mask) {
                return mask.equals("*");
        }

        private boolean isAvailableForSubMask(String address, String remoteIP) {
                if (address.equals(remoteIP)) return true;
                if (address.contains(".")) {
                        // mask or address goes
                        if (address.contains("*")) {
                                // mask goes
                                if (isMaskMatches(address, remoteIP)) return true;
                        } else {
                                // ip goes or error
                                if (address.equals(remoteIP)) return true;
                        }
                } else {
                        // comp class goes or error
                        if (isClassRoomContainIPAddress(address, remoteIP)) return true;
                }
                return false;
        }

        // TODO 192.168.* OR 192.168.70.* OR 192.*
        private boolean isMaskMatches(String address, String remoteIP) {
                String[] array = address.split("\\*");
                // find out the position of first *
                // cut out the values from remoteIP left from *
                // equals
                return false;
        }

        private boolean isClassRoomContainIPAddress(String classRoomID, String remoteIP) {
                Long classId;
                try {
                        classId = Long.parseLong(classRoomID);
                } catch (NumberFormatException e) {
                        return false;
                }
                ClassRoom classRoom = classDAO.findOneWithComputers(classId);
                List<Computer> computers = classRoom.getComputers();
                for (Computer computer : computers) {
                        if (computer.getIp().equals(remoteIP)) return true;
                }
                return false;
        }
}
