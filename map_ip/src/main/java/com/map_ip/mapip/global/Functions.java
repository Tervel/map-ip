package com.map_ip.mapip.global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static String htmlGetIPToString(String ip) throws IOException {
        URL url = new URL("http://ip-api.com/json/" + ip);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }


}
