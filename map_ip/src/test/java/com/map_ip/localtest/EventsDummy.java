package com.map_ip.localtest;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.map_ip.localtest.LocalTestConstants.*;
import static com.map_ip.mapip.global.Constants.*;

/**
 * Author: Thuan Nguyen
 */

public class EventsDummy {
    private static Table eventsTable;

    public EventsDummy(DynamoDB dynamoDB){
        this(dynamoDB, TABLE_NAME_EVENTS);
    }

    public EventsDummy(DynamoDB dynamoDB, String tableName){
        eventsTable = dynamoDB.getTable(tableName);
    }

    public static void createAndPopulateEventsTable(DynamoDB dynamoDB) throws InterruptedException {
        File eventsCsv = new File(
                TESTDATA_BASEDIR + "/"
                        + TESTDATA_LOCATION + "/"
                        + EVENTS_FILENAME);

        List<List<String>> csvStringList = CSVHandler.getCsvStringList(eventsCsv);
        Table table = createEventsTable(dynamoDB);
        insertCsvStringToDynamoDB(csvStringList, table);
    }

    private static Table createEventsTable(DynamoDB dynamoDB) {
        Table table = null;

        //Create table for login attempts
        try {
            System.out.println("Creating table: " + TABLE_NAME_EVENTS + "\n");
            table = dynamoDB.createTable(TABLE_NAME_EVENTS,
                    Arrays.asList(
                            new KeySchemaElement(KEY_IP_NAME, KeyType.HASH),
                            new KeySchemaElement(KEY_DATETIME_NAME, KeyType.RANGE)
                    ),
                    Arrays.asList(
                            new AttributeDefinition(KEY_DATETIME_NAME, ScalarAttributeType.S),
                            new AttributeDefinition(KEY_IP_NAME, ScalarAttributeType.S)),
                    new ProvisionedThroughput(50L, 50L));

            table.waitForActive();
            System.out.println("\nCREATED TABLE: " + TABLE_NAME_EVENTS + "\n");
        } catch (InterruptedException e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
        return table;
    }

    private static void insertCsvStringToDynamoDB(List<List<String>> csvStringList, Table table) {
        for (int i = 1; i < csvStringList.size() - 1; i++) {
            System.out.println("\nINSERTING ITEM NUMBER: " + i + "\n");
            table.putItem(
                    new Item()
                            .withPrimaryKey(KEY_IP_NAME, csvStringList.get(i).get(0)                            )
                            .withString(KEY_EVENT_TYPE_NAME, csvStringList.get(i).get(1))
                            .withString(KEY_DATETIME_NAME, csvStringList.get(i).get(2))
            );
        }
    }


}
