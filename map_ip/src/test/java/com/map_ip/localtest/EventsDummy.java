package com.map_ip.localtest;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.map_ip.mapip.db.EventsTable;

import java.io.File;
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
        Table table = EventsTable.createEventsTable(dynamoDB);
        insertCsvStringToDynamoDB(csvStringList, table);
    }


    private static void insertCsvStringToDynamoDB(List<List<String>> csvStringList, Table table) {
        for (int i = 1; i < csvStringList.size() - 1; i++) {
            System.out.println("\nINSERTING ITEM NUMBER: " + i + "\n");
            table.putItem(
                    new Item()
                            .withPrimaryKey(KEY_IP_NAME, csvStringList.get(i).get(0)                            )
                            .withString(KEY_DATETIME_NAME, csvStringList.get(i).get(2))
                            .withString(KEY_EVENT_TYPE_NAME, csvStringList.get(i).get(1))
            );
        }
    }


}
