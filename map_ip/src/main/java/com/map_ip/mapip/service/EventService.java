package com.map_ip.mapip.service;


import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.map_ip.mapip.model.api.ApiEvent;
import com.map_ip.mapip.repository.DynamoEventMapper;
import com.map_ip.mapip.repository.EventRepository;

import java.util.regex.Pattern;

public class EventService {
    private final EventRepository eventRepository;
    private final DynamoEventMapper dynamoEventMapper = new DynamoEventMapper();


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public boolean addEvent(ApiEvent event){
        //todo lookup calculate attempt#
        return eventRepository.addEvent(dynamoEventMapper.fromApiEventToDynamoEvent(event));
    }

    public boolean contains(ApiEvent eventToSearch) {
        return eventRepository.contains(dynamoEventMapper.fromApiEventToDynamoEvent(eventToSearch));
    }

    public boolean evaluateIP(String ip) {
        if(regexIP(ip)){
            //// TODO
        }
        return false;
    }

    public JSONArray getEventsByIP(String ip) throws JSONException {
        if(regexIP(ip)) return eventRepository.getEventsByIP(ip);
        return null;
    }

    private static boolean regexIP(String ip){
        final String IPADDRESS_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        return Pattern.matches(IPADDRESS_PATTERN, ip);
    }
}
