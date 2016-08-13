package com.map_ip.mapip.global;

public class Constants {
    public final static int DEFAULT_DB_PORT = 1337;
    public final static int DEFAULT_APP_PORT = 8080;

    public final static String DB_AUTH_PATH = "http://localhost:";
    public final static String APP_AUTH_PATH = "http://localhost:";

    public final static String TABLE_NAME_EVENTS = "Events";
    public final static String KEY_EVENT_TYPE_NAME = "type";

    public final static String TABLE_NAME_LOGINATTEMPTS = "LoginAttempts";
    public final static String TABLE_NAME_BLACKLIST = "Blacklist";

    public final static String KEY_IP_NAME = "ip";
    public final static String KEY_ATTEMPT_ID_NAME = "attempt_id";
    public final static String KEY_SUCCESS_NAME = "success";
    public final static String KEY_DATETIME_NAME = "datetime";
}
