package com.map_ip.localtest;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.util.json.JSONObject;
import com.map_ip.mapip.db.EventsTable;
import com.map_ip.mapip.global.Functions;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        populateGeoIP(dynamoDB, csvStringList);
    }

    private static void populateGeoIP(DynamoDB dynamoDB, List<List<String>> csvStringList) {
        Table ipGeoTable = dynamoDB.getTable(TABLE_NAME_IPGEO);
        for(List<String> string: csvStringList) {
            try {
                JSONObject jsonObject = new JSONObject(Functions.htmlGetIPToString(string.get(0)));
                if(jsonObject.getString("status").equals("success")) {
                    ipGeoTable.putItem(new Item()
                            .withPrimaryKey(KEY_IP_NAME, jsonObject.getString("query"))
                            .withDouble("lat", jsonObject.getDouble("lat"))
                            .withDouble("lon", jsonObject.getDouble("lon"))
                            .withString("country", jsonObject.getString("country"))
                            .withString("countryCode", jsonObject.getString("countryCode"))
                            .withString("region", jsonObject.getString("region"))
                            .withString("regionName", jsonObject.getString("regionName"))
                            .withString("city", jsonObject.getString("city"))
                            .withString("zip", jsonObject.getString("zip"))
                            .withString("timezone", jsonObject.getString("timezone"))
                            .withString("isp", jsonObject.getString("isp"))
                            .withString("org", jsonObject.getString("org"))
                            .withString("as", jsonObject.getString("as"))
                            .withString("query", jsonObject.getString("query"))
                    );
                    System.out.println("item retrieved: " + jsonObject.toString());
                    TimeUnit.MILLISECONDS.sleep(500);
                } else {
                    System.out.println("IP status: failed");
                }
            } catch (Exception e) {
                System.out.println("failed to get: " + string.get(0));
                System.out.println(e.getMessage());
            }
        }
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
