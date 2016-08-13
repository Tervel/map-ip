package com.map_ip.mapip.global;

import java.text.SimpleDateFormat;

/**
 * Author: Thuan Nguyen on 12/08/2016.
 */
public class Functions {
    /***
     * Get the current time in milliseconds
     */
    public static String getCurrentTime(){
        final String DATE_FORMAT = "YYYY-MM-DD, HH-mm-ss.SSS";
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sDate = new SimpleDateFormat(DATE_FORMAT);
        return sDate.format(currentTime);
    }
}
