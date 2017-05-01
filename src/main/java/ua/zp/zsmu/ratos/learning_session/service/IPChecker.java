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
         * @param mask - a semicolon separated string signifying classes,
         *             IP-addresses and IP-masks where this scheme is available
         * @param remoteIP
         * @param classRooms - IDs of class room(s) the given IP address belongs to. More often only one class room
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

        private boolean isAvailableForSubMask(String mask, String remoteIP, List<Integer> classRooms) {
                if (mask.equals(remoteIP)) return true;
                if (mask.contains(".")) {// mask or address goes
                        if (mask.contains("*")) {// mask goes
                                if (isMaskMatches(mask, remoteIP)) return true;
                        } else {// ip goes or error
                                if (mask.trim().equals(remoteIP)) return true;
                        }
                } else {// comp class goes or error
                        if (isClassRoomContainIPAddress(mask, remoteIP, classRooms)) return true;
                }
                return false;
        }
        // Algorithm:
        // 1) find out the position of first occurrence of '*' symbol
        // 2) cut out the values from remoteIP left from '*'
        // 3) compare through equals
        // Examples: 192.* OR 192.168.* OR 192.168.70.*
        private boolean isMaskMatches(String mask, String remoteIP) {
                int index = mask.indexOf("*");
                String subAddress = mask.substring(0, index);
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
