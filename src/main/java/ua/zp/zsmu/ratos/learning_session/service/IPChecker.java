package ua.zp.zsmu.ratos.learning_session.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Checks if a given scheme is available from a remote IP-address
 * Created by Andrey on 28.04.2017.
 */
@Service
public class IPChecker {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(IPChecker.class);

        /**
         * A scheme contains a string separated by semicolon (;) which specifies classes,
         * IP-addresses and IP-masks from which the scheme is available
         * e.g. :
         * 1) * - available for everybody
         * 2) 14;15;25;33 - available for computer classes with  id=14, id = 15 etc.
         * 3) 192.168.* - available for the whole mask
         * 4) 192.168.70.140; 192.168.70.141; 192.168.70.142 - available only for these 3 specified IP-addresses
         * 5) 14;25;192.168.70.*; 192.168.1.1 - available for 2 comp. classes, mask and one specified IP-address
         * @param mask
         * @param remoteIP
         * @param classRooms
         * @return
         */
        public boolean isSchemeAvailableFromIPAddress(String mask, String remoteIP, List<Integer> classRooms) {
                if (isAvailableForAll(mask)) return true;
                // get an array of String
                String[] addresses = mask.split(";");
                for (String address : addresses) {
                        if (isAvailableForSubMask(address, remoteIP, classRooms)) return true;
                }
                return false;
        }

        private boolean isAvailableForAll(String mask) {
                return mask.trim().equals("*");
        }

        private boolean isAvailableForSubMask(String address, String remoteIP, List<Integer> classRooms) {
                if (address.equals(remoteIP)) return true;
                if (address.contains(".")) {// mask or address goes
                        if (address.contains("*")) {// mask goes
                                if (isMaskMatches(address, remoteIP)) return true;
                        } else {// ip goes or error
                                if (address.trim().equals(remoteIP)) return true;
                        }
                } else {// comp class goes or error
                        if (isClassRoomContainIPAddress(address, remoteIP, classRooms)) return true;
                }
                return false;
        }
        // find out the position of first occurrence of '*' symbol
        // cut out the values from remoteIP left from '*'
        // equals
        // Examples: 192.168.* OR 192.168.70.* OR 192.*
        private boolean isMaskMatches(String address, String remoteIP) {
                int index = address.indexOf("*");
                String subAddress = address.substring(0, index);
                String subRemoteIP = remoteIP.substring(0, index);
                return subAddress.trim().equals(subRemoteIP);
        }

        private boolean isClassRoomContainIPAddress(String classRoomID, String remoteIP, List<Integer> classRoomIDs) {
                Integer parsedClassRoomId;
                try {
                        parsedClassRoomId = Integer.parseInt(classRoomID.trim());
                } catch (NumberFormatException e) {
                        return false;
                }
                LOGGER.info("Check classes IDs: " + classRoomID);
                for (Integer classRoomId : classRoomIDs) {
                        if (classRoomId.equals(parsedClassRoomId)) return true;
                }
                return false;
        }
}
