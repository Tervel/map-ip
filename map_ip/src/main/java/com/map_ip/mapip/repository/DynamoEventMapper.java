package com.map_ip.mapip.repository;


import com.amazonaws.services.dynamodbv2.document.Item;
import com.map_ip.mapip.global.Constants;
import com.map_ip.mapip.model.DynamoEvent;
import com.map_ip.mapip.model.api.ApiEvent;

public class DynamoEventMapper {

    // TODO datetime mapping // DATE_TIME_FORMAT.parseDateTime(dynamoItem.getString(Constants.KEY_DATETIME_NAME)
    public DynamoEvent fromItemToDynamoEvent(Item dynamoItem) {
        return new DynamoEvent(
                dynamoItem.getString(Constants.KEY_IP_NAME),
                dynamoItem.getString(Constants.KEY_EVENT_TYPE_NAME),
                dynamoItem.getString(Constants.KEY_DATETIME_NAME)
        );
    }

    //todo fix attempt is hardcoded here
    public DynamoEvent fromApiEventToDynamoEvent(ApiEvent event) {
        return new DynamoEvent(event.getIp(), event.getType(), event.getDatetime());
    }
}
