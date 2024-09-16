package com.greenish.web_portal.Utils;

import com.greenish.web_portal.constant.SingleValueLookupConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateTimeUtils {

    public static final DateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER_AM_PM = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
    public static final DateTimeFormatter LOCAL_DATE_TIME_MMM_AM_PM = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
    public static final DateTimeFormatter LOCAL_DATE_TIME_MMM = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // use for date to string
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER_YYYY_M_D = DateTimeFormatter.ofPattern("yyyy-M-d"); // use for string to date for safety
    public static final DateTimeFormatter LOCAL_TIME_FORMATTER_AM_PM = DateTimeFormatter.ofPattern("hh:mm:ss a");
    public static final DateTimeFormatter LOCAL_TIME_FORMATTER_HM_AM_PM = DateTimeFormatter.ofPattern("hh:mm a");
    public static final DateTimeFormatter LOCAL_TIME_FORMATTER_HM = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeUtils() {
    }

    /**
     * This method used convert string to date
     *
     * @param str String date value
     * @return date Date
     */
    public static Date strToDt(String str) {
        Date dt = null;
        try {
            if (str != null) {
                dt = DATA_FORMAT.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static LocalDateTime strToLocalDt(String str) {
        LocalDateTime dt = null;
        try {
            if (str != null) {
//                dt = DATA_FORMAT.parse(str);
                dt = LocalDateTime.parse(str, LOCAL_DATE_TIME_FORMATTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }


    public static LocalDate strToLocalDate(String str) {
        LocalDate dt = null;
        try {
            if (str != null) {
                dt = LocalDate.parse(str, LOCAL_DATE_FORMATTER_YYYY_M_D);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * This method used convert date to string
     *
     * @param dt date
     * @return string Date
     */
    public static String dateToUiDateStr(LocalDate dt) {
        String str = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (dt != null) {
            str = dateFormatter.format(dt);
        }
        return str;
    }

    // return  30-AUG-2024
    public static String dateToUiDateMonthStr(LocalDate date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy"); //30-AUG-2024
            return date.format(formatter);
        }
        return null;
    }

    /**
     * @param dt
     * @return 31-Jul-2024 00:12 AM
     */
    public static String dateToUiDateMonthStr(LocalDateTime dt) {
        String str = null;
        if (dt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
            str = formatter.format(dt);
        }
        return str;
    }


    /**
     * This method used convert date to string
     *
     * @param dt date
     * @return string Date
     */
    public static String localDtToString(LocalDateTime dt) {
        String str = null;
        if (dt != null) {
            str = LOCAL_DATE_TIME_FORMATTER.format(dt);
        }
        return str;
    }

    /**
     * This method used convert date to string
     *
     * @param dt date
     * @return string Date
     */
    public static String localDtToStrHourMin(LocalDateTime dt) {
        String str = null;
        if (dt != null) {
            str = LOCAL_TIME_FORMATTER_HM_AM_PM.format(dt);
        }
        return str;
    }


    /**
     * @param dt
     * @return
     */
    public static String localDtToStrAMPM(LocalDateTime dt) {
        String str = null;
        if (dt != null) {
            str = LOCAL_DATE_TIME_FORMATTER_AM_PM.format(dt);
        }
        return str;
    }


    public static String localDtToStrMMM(LocalDateTime dt) {
        String str = null;
        if (dt != null) {
            str = LOCAL_DATE_TIME_MMM.format(dt);
        }
        return str;
    }

    public static String localDtToStrMMM(LocalDate dt) {
        String str = null;
        if (dt != null) {
            str = LOCAL_DATE_TIME_MMM.format(dt);
        }
        return str;
    }


    /**
     * This method used convert date to string
     *
     * @param dt date
     * @return string Date
     */
    public static String localDtToString(LocalDate dt) {
        String str = null;
        if (dt != null) {
            str = LOCAL_DATE_FORMATTER_YYYY_MM_DD.format(dt);
        }
        return str;
    }

    public static LocalDate stringToLocalDt(String dt) {
        LocalDate str = null;
        if (dt != null) {
//            str = LOCAL_DATE_FORMATTER_YYYY_MM_DD.format(dt);
            str = LocalDate.parse(dt, LOCAL_DATE_FORMATTER_YYYY_MM_DD);
        }
        return str;
    }

    /**
     * 24 format time validation
     *
     * @param time
     * @return
     */
    public static boolean isValidTime24Format(String time) {
        // Regex to check valid time in 24-hour format.
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the time is empty
        // return false
        if (time == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given time
        // and regular expression.
        Matcher m = p.matcher(time);

        // Return if the time
        // matched the ReGex
        return m.matches();

    }

    // input value is dd-MMM-yyyy [01-APR-2023]
    // output : 01-Apr-2023
    public static String capitalizedMonthName(String dateText) {
        if (dateText == null) {
            return null;
        }

        String[] arrDate = dateText.split("-");
        String month = StringUtils.capitalize(arrDate[1].toLowerCase()); // get the remaining letters

        return arrDate[0] + "-" + month + "-" + arrDate[2];
    }

    public static String convertTime24ToTime12(String time24) {
        if (isValidTime24Format(time24)) {
            LocalTime localTime = LocalTime.parse(time24);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            return localTime.format(dateTimeFormatter);
        }
        return null;
    }

    public static boolean isBetween(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate);
    }

    public static boolean isBetweenOrEqual(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return (dateToCheck.isEqual(startDate) || dateToCheck.isEqual(endDate))
                || (dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate));
    }

    public static boolean isAfterOrEqual(LocalDate dateToCheck, LocalDate inputDate) {
        return dateToCheck.isAfter(inputDate) || dateToCheck.isEqual(inputDate);
    }

    public static boolean isBeforeOrEqual(LocalDate dateToCheck, LocalDate inputDate) {
        return dateToCheck.isBefore(inputDate) || dateToCheck.isEqual(inputDate);
    }

    public static LocalTime stringToLocalTime(String timeString) {
        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return LocalTime.of(hours, minutes);
    }

    /*public static Long timeToSlotType(String timeString) {
        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        if (6 <= hours && hours <= 12) {
            return SingleValueLookupConstant.MORNING;
        } else if (13 <= hours && hours <= 16) {
            return SingleValueLookupConstant.DAYTIME;
        } else {
            return SingleValueLookupConstant.EVENING;
        }
    }*/

    public static Long timeToSlotType(String timeString) {
        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        if (0 <= hours && hours < 12) {
            return SingleValueLookupConstant.MORNING;
        } else if (12 <= hours && hours < 18) {
            return SingleValueLookupConstant.DAYTIME;
        } else {
            return SingleValueLookupConstant.EVENING;
        }
    }

    public static String convertTimeFormat(String time) {
        // Define the input and output formatters
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Parse the input time string to a LocalTime object
        LocalTime localTime = LocalTime.parse(time, inputFormatter);

        // Format the LocalTime object to the desired output format
        String formattedTime = localTime.format(outputFormatter);

        // Ensure the output is in uppercase for AM/PM notation
        return formattedTime.toUpperCase();
    }

    public static String getTimeInAmPmFormat(LocalDateTime dateTime) {
        // Define the formatter for the desired time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");

        // Format the LocalDateTime to the desired time format
        return dateTime.format(formatter);
    }


    public static String formatTimeTo24(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("dateTime cannot be null");
        }
        DateTimeFormatter formatter = LOCAL_TIME_FORMATTER_HM;
        return dateTime.format(formatter);
    }

    public static String convertMinToHrMin(Long totalMinutes) {
        if (totalMinutes == null) {
            return "0 min";
        } else {
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;
            StringBuilder result = new StringBuilder();

            if (hours > 0) {
                result.append(hours).append(" hr");
            }

            if (minutes > 0) {
                result.append(" ").append(minutes).append(" min");
            }

            if (result.length() == 0) {
                return "0 min";
            } else {
                return result.toString();
            }
        }
    }

}
