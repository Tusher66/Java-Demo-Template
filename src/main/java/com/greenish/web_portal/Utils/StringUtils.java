package com.greenish.web_portal.Utils;

import org.apache.commons.text.CaseUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {

    public static String convertString(Long value) {
        return value != null ? value.toString() : null;
    }

    public static String convertString(Integer value) {
        return value != null ? value.toString() : null;
    }

    /**
     * convert underscore string to camel case string
     *
     * @param uStr :: underscore string
     * @return camel case string
     */
    public static String underscoreToCamelCase(String uStr) {
        if (uStr != null && !uStr.isEmpty()) {
            return CaseUtils.toCamelCase(uStr, false, '_');
        } else {
            return null;
        }

    }

    public static boolean isValidMobileNumber(String mobileNo) {
        // Regex to check valid mobile number.
        String regex = "^(?:\\+88|88)?(01[3-9]\\d{8})$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the mobile number is empty
        // return false
        if (mobileNo == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given mobile number
        // and regular expression.
        Matcher m = p.matcher(mobileNo);

        // Return if the mobile number
        // matched the ReGex
        return m.matches();

    }

    public static boolean containsWords(String inputString, String[] items) {
        boolean found = false;
        if (inputString == null || items == null || items.length == 0) {
            return false;
        }
        for (String item : items) {
            if (inputString.contains(item)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static String convertToString(Object input) {
        if (Objects.isNull(input) || input.equals("null") || input.equals("")) {
            return "";
        }
        return String.valueOf(input);
    }

    public static String changeChassisToAsterisk(String chassisNo) {
        if (StringUtils.hasText(chassisNo) && chassisNo.length() > 4) {
            return chassisNo.substring(0, chassisNo.length() - 4) + "****";
        }
        return chassisNo;
    }

    public static String replaceNonAsciiChar(String applicantNameEng) {
        return applicantNameEng.replaceAll("[^\\x20-\\x7F]", "");
    }

    public static String getFirstPartSplit(String input) {
        String[] nameParts = input.split("\\s+");
        return nameParts.length > 0 ? nameParts[0] : "";
    }

    public static String getLastPartSplit(String input) {
        String[] nameParts = input.split("\\s+",2);
        return nameParts.length > 1 ? nameParts[1] : "";
    }

    public static Long getBookingReqNo(String search) {
        Long bookingId = null;
        if (StringUtils.hasText(search) && search.length() > 9) {
            try {
                bookingId = Long.parseLong(search);
            } catch (NumberFormatException e) {
            }
        }
        return bookingId;
    }

}
