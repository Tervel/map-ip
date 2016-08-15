package com.map_ip.mapip.global;

public class Constants {
    public final static int DEFAULT_DB_PORT = 1337;
    public final static int DEFAULT_APP_PORT = 8080;

    public final static String DB_AUTH_PATH = "http://localhost:";
    public final static String APP_AUTH_PATH = "http://localhost:";

    public final static String TABLE_NAME_BLACKLIST = "Blacklist";
    public final static String TABLE_NAME_EVENTS = "Events";
    public final static String TABLE_NAME_EVENTS_QUEUE = "EventsQueue";
    public final static String TABLE_NAME_IPQUEUE = "IpQueue";
    public final static String TABLE_NAME_IPGEO = "IpGeo";
    public final static String KEY_EVENT_TYPE_NAME = "type";
    public final static String KEY_IP_NAME = "ip";
    public final static String KEY_DATETIME_NAME = "datetime";

    public final static String DATE_TIME_FORMAT = "YYYY-MM-dd HH:mm:ss.SSS";

    public static final String KEY_IP_COUNTRYCODE = "countrycode";
    public static final String KEY_IP_LAT = "lat";
    public static final String KEY_IP_LON = "lon";
    public static final String KEY_IP_UPDATE_TIME = "last-update";
}
