package com.greenish.web_portal.constant;

public class DbConstant {
    public static final String APP_TITLE = "ACE LUXURY CAR WASH";

    public final static Long SUPER_ADMIN = 1L;
    public final static Long FACILITY_MANAGER = 2L;
    public final static Long SPECIALIST = 3L;
    public final static Long CLIENT = 4L;

    public final static Long OFFER_CUPON_BASE = 1L;
    public final static Long OFFER_DURATION_BASE = 2L;

    public static final int DEFAULT_SHOW_SHIFT_DAYS = 15; // show last 15 days shift
    public static final String SOCIAL_PLATFORM_USER_PASSWORD = "Sp#1234!";
    public static final int DEFAULT_OTP_EXPIRE_2MIN = 120; // 2min

    public final static Long VEHICLE_OTHER_COLOR_ID = 99L;
    public final static String BOOKING_PAYMENT_REQUEST_HTTP_PREFIX = "http://192.168.78.44:9090";
    public final static String BOOKING_PAYMENT_REQUEST_HTTPS_PREFIX = "https://192.168.78.44:9090";

    public static final Long REPORT_MAIN_MENU_ID = 8L;

    public static final String WORKFLOW_DECISION_PENDING = "PENDING";
    public static final String WORKFLOW_DECISION_APPROVED = "APPROVED";
    public static final String WORKFLOW_DECISION_REJECTED = "REJECTED";

    public static final String ATTENDANCE_DISTANCE = "ATTENDANCE_DISTANCE";

    public static final String INTERNAL_PLATFORM_FEE = "PLATFORM_FEE";

    public static final Long PRE_EXPIRE_MEMBERSHIP_DEFAULT_DAYS = 5L;
    public static final Long DISABLE_TEMPORARY_VEHICLE_DEFAULT_DAYS = 30L;

    public final static Long MEMBERSHIP_TYPE_REGULAR = 1L;
    public final static Long MEMBERSHIP_TYPE_EXCLUSIVE = 2L;

    public final static Integer EXCLUSIVE_MEMBERSHIP_EXPIRE_DAYS = -1;
    public final static Integer DEFAULT_EXCLUSIVE_USER_MEM_EXPIRE_DAYS = 356 * 10;

    public final static Integer MAX_NUMBER_OF_TIMES = 50;

    public final static Integer DELETE_UNPAID_SERVICE_BOOKING_TIME_MIN = 12;

    public final static Integer DELETE_UNPAID_USER_MEMBERSHIP_TIME_MIN = 30;

    public final static Integer MIN_NUMBER_OF_TIMES = 0;

    public static final String PAYMENT_NOT_APPLICABLE = "P";

    public static final String MEMBERSHIP_PENDING_BOOKING_MSG = "THANKS FOR YOUR BOOKING CONFIRMATION, " +
            "AS YOUR SLOT ALREADY BOOKED BUT YOU COULD STILL AVAIL IT! PLEASE WAIT FOR SLOT APPROVAL!";

    public static final String BOOKING_APPROVAL_GENERAL_WF = "BOOKING APPROVAL GENERAL WF";

}