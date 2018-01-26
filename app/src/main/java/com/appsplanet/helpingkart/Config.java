package com.appsplanet.helpingkart;


public class Config {

    public static String URL = "http://helpingcart.com/need-hlp-service";
    public static String getRegistration = URL + "/request_sms.php?";
    public static String getOTP = URL + "/verify_otp.php?";
    public static String getLogin = URL + "/login.php?";
    public static String getCarBooking = URL + "/car_booking.php?";
    public static String getVehicleMechanic = URL + "/vehicle_mechanic.php?";
    public static String getFoodMenu = URL + "/food_menu.php?";
    public static String getCategory = URL + "/request_category.php?";
    public static String getFoodBooking = URL + "/food_booking.php?";
    public static String getOrderHistory = URL + "/booking_history.php?";
    public static String getProfileUser = URL + "/edit_profile.php?";
    public static String getHomeBanner = URL + "/banner.php?";

    public static String LOGIN_DEVICE_TOKEN = "login_device_token";


    public static String DB_LOGIN = "login";
    public static String DB_LOGIN_DONE = "login_done";
    public static String DB_LOGIN_MOBILE = "login_mobile";

    public static String DB_REGISTER_NAME = "register_name";
    public static String DB_REGISTER_EMAIL = "register_email";
    public static String DB_REGISTER_MOBILE = "register_mobile";
    public static String DB_Updated_address = "updated_address";
    public static String DB_REFERAL_CODE = "referal_code";
    public static String DB_REFERAL_POINTS = "referal_points";




    public static String getNotification = URL + "/notification_history.php";

    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
}
