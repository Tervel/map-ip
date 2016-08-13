package com.map_ip.mapip.db;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

import static com.map_ip.mapip.global.Constants.*;

/**
 * Author: Thuan Nguyen on 13/08/2016.
 *
 *
 * EVENTSQUEUETABLE
 * Events in queue waiting to be processed
 */
public class IPQueueTable {
    public static Table createEventsQueueTable(DynamoDB dynamoDB) throws InterruptedException {
        return createEventsQueueTable(dynamoDB, TABLE_NAME_EVENTS_QUEUE);
    }

    public static Table createEventsQueueTable(DynamoDB dynamoDB, String tableName) throws InterruptedException {
        Table eventsQueueTable = null;
        System.out.println("Creating table: " + tableName + "\n");
        eventsQueueTable = dynamoDB.createTable(tableName,
                Arrays.asList(
                        new KeySchemaElement(KEY_IP_NAME, KeyType.HASH)
                ),
                Arrays.asList(
                        new AttributeDefinition(KEY_IP_NAME, ScalarAttributeType.S)
                ),
                new ProvisionedThroughput(50L, 50L));

        eventsQueueTable.waitForActive();
        System.out.println("\nSuccessfully Created Table: " + TABLE_NAME_EVENTS + "\n");
        return eventsQueueTable;
    }
}
