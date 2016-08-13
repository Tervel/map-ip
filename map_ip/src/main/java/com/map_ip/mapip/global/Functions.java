package com.map_ip.mapip.global;

import java.text.SimpleDateFormat;

import static com.map_ip.mapip.global.Constants.DATE_TIME_FORMAT;

/**
 * Author: Thuan Nguyen on 12/08/2016.
 */
public class Functions {
    /***
     * Get the current time in milliseconds
     */
    public static String getCurrentTime(){
        final String DATE_FORMAT = DATE_TIME_FORMAT;
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sDate = new SimpleDateFormat(DATE_FORMAT);
        return sDate.format(currentTime);
    }
}
