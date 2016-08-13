package com.map_ip.map_ip;


import com.map_ip.mapip.model.api.ApiEvent;

import static com.map_ip.localtest.LocalTestConstants.*;
import static com.map_ip.mapip.global.Constants.TABLE_NAME_EVENTS;

public class TestDataBuilder {

    private static final String IP = EVENT_CASE_IP;
    private static final String TYPE = EVENT_CASE_TYPE;
    private static final String DATETIME = EVENT_CASE_DATETIME;
    public static ApiEvent defaultApiEvent(){
        return new ApiEvent(IP, TABLE_NAME_EVENTS, DATETIME);
    }
}
