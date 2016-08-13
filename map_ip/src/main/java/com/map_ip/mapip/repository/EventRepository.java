package com.map_ip.mapip.repository;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.map_ip.mapip.model.DynamoEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static com.map_ip.mapip.global.Constants.*;


public class EventRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepository.class);

    private final Table eventTable;
    private final DynamoEventMapper eventMapper = new DynamoEventMapper();

    public EventRepository(DynamoDB dynamoDB) {
        this.eventTable = dynamoDB.getTable(TABLE_NAME_EVENTS);
    }

    public boolean addEvent(DynamoEvent event) {
        try {

            PutItemOutcome outcome = eventTable.putItem(
                    new Item()
                            .withPrimaryKey(KEY_IP_NAME, event.getIp())
                            .withString(KEY_DATETIME_NAME, event.getDatetime())
                            .withString(KEY_EVENT_TYPE_NAME, event.getType())
            );
            LOGGER.info("PutItem succeeded:\n" + outcome.getPutItemResult());
            return true;
        } catch (Exception e) {
            LOGGER.error("Unable to add item: " + event.getType() + " " + event.getIp(), e);
            return false;
        }
    }

    public boolean contains(DynamoEvent eventToSearch) {
        try {
            Item outcome = eventTable.getItem(
                    KEY_IP_NAME, eventToSearch.getIp()
            );
            DynamoEvent resultEvent = eventMapper.fromItemToDynamoEvent(outcome);
            return (resultEvent != null && !StringUtils.isBlank(resultEvent.getIp()));
        } catch (Exception e) {
            LOGGER.error("Unable to find item: " + eventToSearch.getType() + " " + eventToSearch.getIp(), e);
        }
        return false;
    }

    public JSONArray getEventsByIP(String ip) throws JSONException {
        LOGGER.debug("ATTEMPTING TO GET EVENTS");
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(KEY_IP_NAME + " = :ip")
                .withValueMap(new ValueMap()
                        .withString(":ip", ip)
                );
        ItemCollection<QueryOutcome> events = eventTable.query(querySpec);
        Iterator<Item> iterator = events.iterator();
        JSONArray jsonArray = new JSONArray();
        while(iterator.hasNext()) {
            JSONObject jsonObject = new JSONObject(iterator.next().toJSON());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

}
