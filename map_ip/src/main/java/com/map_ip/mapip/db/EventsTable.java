package com.map_ip.mapip.db;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

import static com.map_ip.mapip.global.Constants.*;

/**
 * Author: Thuan Nguyen
 */
public class EventsTable {
    private static DynamoDB dynamoDB;
    private static Table eventsTable;
    private static String eventsTableName;

    public EventsTable(DynamoDB dynamoDB) {
        this(dynamoDB, TABLE_NAME_EVENTS);
    }

    public EventsTable(DynamoDB dynamoDB, String eventsTableName) {
        this.dynamoDB = dynamoDB;
        this.eventsTableName = eventsTableName;

        //IF TABLE DOES NOT EXIST, CREATE ONE
        try {
            this.createEventsTable();
        } catch (InterruptedException e) {
            System.err.println("ERROR: " + EventsTable.class.getCanonicalName());
            System.err.println("Failed to create table: " + e.getMessage());
        }
    }

    public Table createEventsTable() throws InterruptedException {
        return createEventsTable(this.dynamoDB, this.eventsTableName);
    }

    public static Table createEventsTable(DynamoDB dynamoDB, String tableName) throws InterruptedException {
        Table eventsTable = null;
        System.out.println("Creating table: " + tableName + "\n");
        eventsTable = dynamoDB.createTable(tableName,
                Arrays.asList(
                        new KeySchemaElement(KEY_IP_NAME, KeyType.HASH),
                        new KeySchemaElement(KEY_DATETIME_NAME, KeyType.RANGE)
                ),
                Arrays.asList(
                        new AttributeDefinition(KEY_IP_NAME, ScalarAttributeType.S),
                        new AttributeDefinition(KEY_EVENT_TYPE_NAME, ScalarAttributeType.S),
                        new AttributeDefinition(KEY_DATETIME_NAME, ScalarAttributeType.S)
                ),
                new ProvisionedThroughput(50L, 50L));

        eventsTable.waitForActive();
        System.out.println("\nSuccessfully Created Table: " + TABLE_NAME_EVENTS + "\n");
        return eventsTable;
    }

    public boolean deleteTable(){
        return false;
    }
}
